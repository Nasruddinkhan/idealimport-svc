package ca.com.idealimport.service.purchaseorder.service.impl;

import ca.com.idealimport.common.mapper.PurchaseOrderItemMapper;
import ca.com.idealimport.common.mapper.PurchaseOrderMapper;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.repository.PurchaseOrderRepository;
import ca.com.idealimport.service.purchaseorder.service.PurchaseOrderService;
import ca.com.idealimport.service.users.control.UserControl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
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
                .convertProductItemToProductCreationResponse(purchaseOrderRes.getPurchaseOrderId().getPurchaseOrderId());
    }
}
