package ca.com.idealimport.service.purchaseorder.service.impl;

import ca.com.idealimport.common.Constants;
import ca.com.idealimport.common.specifications.SpecificationUtils;
import ca.com.idealimport.common.specifications.Specifications;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.SearchPurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderResponseDto;
import ca.com.idealimport.service.purchaseorder.mapper.PurchaseOrderItemMapper;
import ca.com.idealimport.service.purchaseorder.mapper.PurchaseOrderMapper;
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
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PartyControl partyControl;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseOrderItemMapper purchaseOrderItemMapper;
    private final UserControl userControl;
    private final PageUtils pageUtils;

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
