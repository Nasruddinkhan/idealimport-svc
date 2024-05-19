package ca.com.idealimport.service.purchaseorder.service.impl;

import ca.com.idealimport.common.Constants;
import ca.com.idealimport.common.constants.ErrorConstants;
import ca.com.idealimport.common.specifications.SpecificationUtils;
import ca.com.idealimport.common.specifications.Specifications;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.service.ProductService;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItem;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItemIdKey;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItems;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderItemResponseDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.UpdatePurchaseOrderBean;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.SearchPurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderResponseDto;
import ca.com.idealimport.service.purchaseorder.entity.enums.ShippingStatus;
import ca.com.idealimport.service.purchaseorder.mapper.PurchaseOrderItemMapper;
import ca.com.idealimport.service.purchaseorder.mapper.PurchaseOrderMapper;
import ca.com.idealimport.service.purchaseorder.repository.PurchaseOrderItemRepository;
import ca.com.idealimport.service.purchaseorder.repository.PurchaseOrderItemsRepository;
import ca.com.idealimport.service.purchaseorder.repository.PurchaseOrderRepository;
import ca.com.idealimport.service.purchaseorder.service.PurchaseOrderService;
import ca.com.idealimport.service.users.control.UserControl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseOrderItemMapper purchaseOrderItemMapper;
    private final UserControl userControl;
    private final PageUtils pageUtils;
    private final ProductService productService;
    private final PurchaseOrderItemRepository itemRepository;
    private final PurchaseOrderItemsRepository orderItemsRepository;
    private final PartyControl partyControl;

    @Override
    @Transactional
    public PurchaseOrderResponse savePurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        return Optional.ofNullable(purchaseOrderDto)
                .map(this::processPurchaseOrder)
                .orElseThrow(() -> new RuntimeException("purchase order cannot be empty"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderResponseDto> getPurchaseOrder(int page, int size, SearchPurchaseOrderDto searchProductDto) {
        log.debug("PurchaseOrderServiceImpl.getPurchaseOrder page = {}, size = {}," +
                " searchProductDto = {}", page, size, searchProductDto);
        final var productPage = pageUtils.getPageableOrder(page, size, Sort.Direction.DESC.name(), Constants.CREATED_DATE);
        Specification<PurchaseOrder> specification = buildWhereConditions(searchProductDto, Boolean.TRUE);
        var products = purchaseOrderRepository.findAll(specification, productPage)
                .map(purchaseOrderMapper::convertPurchaseOrderToDtoResponse);
        log.debug("PurchaseOrderServiceImpl.getProducts products ={}", products);
        return products;
    }

    @Override
    @Transactional
    public Map<String, String> movePurchaseOrderIntoProduct(List<String> purchaseOrderId) {
        List<UpdatePurchaseOrderBean> items = purchaseOrderRepository.findAllById(purchaseOrderId).stream()
                .flatMap(purchaseOrder -> purchaseOrder.getPurchaseOrderItems().stream())
                .flatMap(purchaseOrderItem -> purchaseOrderItem.getPurchaseOrderItem().stream()
                        .map(item -> UpdatePurchaseOrderBean.builder()
                                .itemCode(purchaseOrderItem.getItemCode())
                                .orderItem(item)
                                .party(purchaseOrderItem.getPurchaseOrderItemIdKey().getParty())
                                .build()))
                .toList();
        items.stream().forEach(productService::updateProductStock);
        purchaseOrderRepository.findAllById(purchaseOrderId)
                .stream().map(purchaseOrderMapper::mapShippingStatus)
                .map(purchaseOrderRepository::save).forEach(e ->
                        System.out.println(String.format("%s lot has been updated with %s shipping status", e.getLotNumber(), e.getShippingStatus())));
        return Map.of("msg", "Stock has been updated successfully");
    }

    @Override
    @Transactional
    public void deletePurchaseOrderItem(String purchaseOrderId) {
        PurchaseOrderItem orderItem = findPurchaseOrderItem(purchaseOrderId);
        itemRepository.deleteById(purchaseOrderId);
        PurchaseOrderItems items = orderItem.getPurchaseOrderItems();
        items.setTotalQuantity(items.getTotalQuantity() - orderItem.getSubTotal());
        orderItemsRepository.save(items);
        PurchaseOrder order = items.getPurchaseOrder();
        order.setTotalQuantity(order.getTotalQuantity() - orderItem.getSubTotal());
        purchaseOrderRepository.save(order);

    }

    @Override
    @Transactional
    public void deletePurchaseOrderId(String purchaseOrderItemId, Long partyId) {

        PurchaseOrderItems items = orderItemsRepository.findById(
                        new PurchaseOrderItemIdKey(purchaseOrderItemId, partyControl.findParty(partyId)))
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND,
                        String.format(ErrorConstants.PURCHASE_ORDERS_NOT_PRESENT, purchaseOrderItemId)));
        int totalQty = items.getTotalQuantity();
        orderItemsRepository.deleteById(items.getPurchaseOrderItemIdKey());
        PurchaseOrder order = items.getPurchaseOrder();
        order.setTotalQuantity(order.getTotalQuantity() - totalQty);
        purchaseOrderRepository.save(order);
    }

    @Override
    public void deletePurchaseOrder(String purchaseOrderId) {
        purchaseOrderRepository.deleteById(purchaseOrderId);
    }

    @Override
    public List<PurchaseOrderItemResponseDto> findAllItemByPartyAndItemCode(String itemCode, String partyName) {
        Party party = partyControl.findParty(partyName);
        return orderItemsRepository.findByPurchaseOrderItemIdKeyPartyAndItemCode(party, itemCode)
                .stream().filter(e -> e.getPurchaseOrder().getShippingStatus().equals(ShippingStatus.IN_TRANSIT))
                .map(PurchaseOrderItems::getPurchaseOrderItem)
                .map(purchaseOrderItemMapper::getPurchaseOrderItemDto)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(PurchaseOrderItemResponseDto::color, dto -> dto, (dto1, dto2) -> PurchaseOrderItemResponseDto.builder()
                        .color(dto1.color())
                        .s(dto1.s() + dto2.s())
                        .m(dto1.m() + dto2.m())
                        .xl(dto1.xl() + dto2.xl())
                        .xs(dto1.xs() + dto2.xs())
                        .xxl(dto1.xxl() + dto2.xxl())
                        .xxxl(dto1.xxxl() + dto2.xxxl())
                        .mixed(dto1.mixed() + dto2.mixed())
                        .l(dto1.l() + dto1.l())
                        .subTotal(dto1.subTotal() + dto2.subTotal())
                        .build())).values().stream()
                .map(dto -> PurchaseOrderItemResponseDto.builder()
                        .color(dto.color())
                        .s(dto.s())
                        .m(dto.m())
                        .xl(dto.xl())
                        .xs(dto.xs())
                        .xxl(dto.xxl())
                        .xxxl(dto.xxxl())
                        .mixed(dto.mixed())
                        .l(dto.l())
                        .subTotal(dto.subTotal())
                        .build()).toList();

    }

    @Override
    public PurchaseOrderItem findPurchaseOrderItem(String purchaseOrderLineItemId) {
        return itemRepository.findById(purchaseOrderLineItemId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format(ErrorConstants.PURCHASE_ORDER_LINE_NOT_PRESENT, purchaseOrderLineItemId)));
    }

    private Specification<PurchaseOrder> buildWhereConditions(SearchPurchaseOrderDto searchProductDto,
                                                              Boolean isActiveOnly) {
        List<Specification<PurchaseOrder>> specificationsList = new ArrayList<>();
        specificationsList.add(Specifications.fieldProperty(Constants.ACTIVE, isActiveOnly));
        return SpecificationUtils.and(specificationsList);

    }


    private PurchaseOrderResponse processPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {

        final var purchaseOrderId = CommonUtils.getUUID(purchaseOrderDto.purchaseOrderId());
        final var user = userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId());
        final var purchaseOrderEntity = purchaseOrderMapper
                .getPurchaseOrderDtoToEntity(purchaseOrderDto, purchaseOrderId, user);
        final var purchaseOrderItems = purchaseOrderDto.addPurchaseOrderDto().stream().map(e -> purchaseOrderItemMapper
                        .getPurchaseOrderItemsDtoToEntity(purchaseOrderEntity, e, user))
                .toList();
        purchaseOrderItems.stream()
                .forEach(purchaseOrderItem -> purchaseOrderItem.getPurchaseOrderItem().forEach(e -> e.setPurchaseOrderItems(purchaseOrderItem)));
        purchaseOrderEntity.setPurchaseOrderItems(purchaseOrderItems);
        final var purchaseOrderRes = purchaseOrderRepository.save(purchaseOrderEntity);
        return purchaseOrderMapper
                .convertProductItemToProductCreationResponse(purchaseOrderRes.getPurchaseOrderId());
    }
    /*
   private PurchaseOrderResponse processPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
       final String purchaseOrderId = generatePurchaseOrderId(purchaseOrderDto);
       final User user = retrieveUser();
       final PurchaseOrder purchaseOrderEntity = createPurchaseOrderEntity(purchaseOrderDto, purchaseOrderId, user);
       final List<PurchaseOrderItems> purchaseOrderItems = mapPurchaseOrderItems(purchaseOrderDto, purchaseOrderEntity, user);
       setPurchaseOrderItems(purchaseOrderItems);
       final PurchaseOrder savedPurchaseOrderEntity = savePurchaseOrder(purchaseOrderEntity);
       return createPurchaseOrderResponse(savedPurchaseOrderEntity);
   }

    private String generatePurchaseOrderId(final PurchaseOrderDto purchaseOrderDto) {
        return CommonUtils.getUUID(purchaseOrderDto.purchaseOrderId());
    }

    private User retrieveUser() {
        return userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId());
    }

    private PurchaseOrder createPurchaseOrderEntity(PurchaseOrderDto purchaseOrderDto, String purchaseOrderId, User user) {
        return purchaseOrderMapper.getPurchaseOrderDtoToEntity(purchaseOrderDto, purchaseOrderId, user);
    }

    private List<PurchaseOrderItems> mapPurchaseOrderItems(PurchaseOrderDto purchaseOrderDto, PurchaseOrder purchaseOrderEntity, User user) {
        return purchaseOrderDto.addPurchaseOrderDto().stream()
                .map(dto -> purchaseOrderItemMapper.getPurchaseOrderItemsDtoToEntity(purchaseOrderEntity, dto, user))
                .toList();
    }

    private void setPurchaseOrderItems(List<PurchaseOrderItems> purchaseOrderItems) {
        purchaseOrderItems.forEach(item ->
                item.getPurchaseOrderItem().forEach(subItem ->
                        subItem.setPurchaseOrderItems(item)
                )
        );
    }

    private PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrderEntity) {
        return purchaseOrderRepository.save(purchaseOrderEntity);
    }

    private PurchaseOrderResponse createPurchaseOrderResponse(PurchaseOrder purchaseOrderEntity) {
        return purchaseOrderMapper.convertProductItemToProductCreationResponse(purchaseOrderEntity.getPurchaseOrderId());
    }
    *
     */
}
