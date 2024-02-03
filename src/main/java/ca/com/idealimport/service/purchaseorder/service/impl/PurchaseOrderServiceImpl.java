package ca.com.idealimport.service.purchaseorder.service.impl;

import ca.com.idealimport.common.Constants;
import ca.com.idealimport.common.mapper.PurchaseOrderItemMapper;
import ca.com.idealimport.common.mapper.PurchaseOrderMapper;
import ca.com.idealimport.common.specifications.SpecificationUtils;
import ca.com.idealimport.common.specifications.Specifications;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponseDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.SearchPurchaseOrderDto;
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
        return Optional.ofNullable(purchaseOrderDto).map(dto ->
                        partyControl.findParty(dto.addPurchaseOrderDto().partyId()))
                .map(foundParty -> this.processPurchaseOrder(foundParty, purchaseOrderDto))
                .orElseThrow(() -> new RuntimeException("purchase order cannot be empty"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderResponseDto> getPurchaseOrder(int page, int size, SearchPurchaseOrderDto searchProductDto) {
        log.debug("PurchaseOrderServiceImpl.getPurchaseOrder page = {}, size = {}," +
                " searchProductDto = {}", page, size, searchProductDto);
        final var productPage = pageUtils.getPageableOrder(page, size, Sort.Direction.DESC.name(), Constants.PURCHASE_ORDER_ID);
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



    private PurchaseOrderResponse processPurchaseOrder(Party foundParty, PurchaseOrderDto purchaseOrderDto) {
        final var generatedProductId = CommonUtils.getUUID(purchaseOrderDto.purchaseOrderId());
        final var productKey = purchaseOrderMapper.getPurchaseOrderIdKey(generatedProductId, foundParty);
        final var user = userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId());
        final var purchaseOrderEntity = purchaseOrderMapper.getPurchaseOrderDtoToEntity(
                productKey, purchaseOrderDto, user);
        final var purchaseOrderItems = purchaseOrderItemMapper
                .convertPurchaseOrderItemDtosToEntity(
                        purchaseOrderEntity, purchaseOrderDto.addPurchaseOrderDto().addPurchaseOrderItemDto(), user);
        purchaseOrderEntity.setPurchaseOrderItems(purchaseOrderItems);
        final var purchaseOrderRes = purchaseOrderRepository.save(purchaseOrderEntity);
        return purchaseOrderMapper
                .convertProductItemToProductCreationResponse(purchaseOrderRes.getPurchaseOrderKey().getPurchaseOrderId());
    }
}
