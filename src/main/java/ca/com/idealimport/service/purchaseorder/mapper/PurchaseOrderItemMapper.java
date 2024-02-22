package ca.com.idealimport.service.purchaseorder.mapper;

import ca.com.idealimport.common.dto.AuditDto;
import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItem;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItemIdKey;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItems;
import ca.com.idealimport.service.purchaseorder.entity.dto.AddPurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.AddPurchaseOrderItemDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderItemResponseDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderItemsResponseDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderLineItemDto;
import ca.com.idealimport.service.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PurchaseOrderItemMapper {
    private final PartyControl partyControl;

    public PurchaseOrderItemIdKey getPurchaseOrderItemIdKey(String uuId, Party party) {
        return PurchaseOrderItemIdKey.builder()
                .purchaseOrderItemId(uuId)
                .party(party)
                .build();
    }

    public PurchaseOrderItems getPurchaseOrderItemsDtoToEntity(PurchaseOrder purchaseOrder,
                                                               AddPurchaseOrderDto purchaseOrderItems,
                                                               User user) {
        final var generatedPurchaseOrderId = CommonUtils.getUUID(purchaseOrderItems.addPurchaseOrderId());
        final var party = partyControl.findParty(purchaseOrderItems.partyId());
        var productKey = getPurchaseOrderItemIdKey(generatedPurchaseOrderId, party);
        return PurchaseOrderItems.builder()
                .purchaseOrderItemIdKey(productKey)
                .totalQuantity(purchaseOrderItems.totalQuantity())
                .itemCode(purchaseOrderItems.itemCode())
                .isActive(Boolean.TRUE)
                .purchaseOrder(purchaseOrder)
                .purchaseOrderItem(purchaseOrderItems.addPurchaseOrderItemDto().stream()
                        .map(e -> convertPurchaseOrderItemToEntity(e, user)).toList())
                .user(user)
                .build();
    }

    public PurchaseOrderItem convertPurchaseOrderItemToEntity(AddPurchaseOrderItemDto addPurchaseOrderItemDto, User user) {

        return PurchaseOrderItem.builder()
                .purchaseOrderLineItemId(CommonUtils.getUUID(addPurchaseOrderItemDto.addPurchaseOrderItemId()))
                .s(addPurchaseOrderItemDto.s())
                .l(addPurchaseOrderItemDto.l())
                .xl(addPurchaseOrderItemDto.xl())
                .m(addPurchaseOrderItemDto.m())
                .xs(addPurchaseOrderItemDto.xs())
                .xxl(addPurchaseOrderItemDto.xxl())
                .xxxl(addPurchaseOrderItemDto.xxxl())
                .mixed(addPurchaseOrderItemDto.mixed())
                .details(addPurchaseOrderItemDto.details())
                .subTotal(addPurchaseOrderItemDto.subTotal())
                .isActive(Boolean.TRUE)
                .user(user)
                .build();

    }

    private PurchaseOrderItemResponseDto convertPurchaseOrderItemToDto(PurchaseOrderItem purchaseOrderItem) {
        return PurchaseOrderItemResponseDto.builder()
                .purchaseOrderItemId(purchaseOrderItem.getPurchaseOrderLineItemId())
                .isActive(purchaseOrderItem.getIsActive())
                .s(purchaseOrderItem.getS())
                .l(purchaseOrderItem.getL())
                .xl(purchaseOrderItem.getXl())
                .m(purchaseOrderItem.getM())
                .xs(purchaseOrderItem.getXs())
                .xxl(purchaseOrderItem.getXxl())
                .xxxl(purchaseOrderItem.getXxxl())
                .mixed(purchaseOrderItem.getMixed())
                .details(purchaseOrderItem.getDetails())
                .subTotal(purchaseOrderItem.getSubTotal())
                .build();

    }

    public List<PurchaseOrderItemResponseDto> getPurchaseOrderItemDto(List<PurchaseOrderItem> purchaseOrderItems) {
        return purchaseOrderItems.stream().map(this::convertPurchaseOrderItemToDto).toList();
    }

    public List<PurchaseOrderItemsResponseDto> getPurchaseOrderItemsResponse(
            List<PurchaseOrderItems> purchaseOrderItems) {
        return purchaseOrderItems.stream().map(this::getPurchaseOrderItems).toList();

    }

    private PurchaseOrderItemsResponseDto getPurchaseOrderItems(PurchaseOrderItems items) {
        PurchaseOrderItemIdKey orderItemIdKey = items.getPurchaseOrderItemIdKey();
        Party party = orderItemIdKey.getParty();
        return PurchaseOrderItemsResponseDto.builder()
                .purchaseOrderItemId(orderItemIdKey.getPurchaseOrderItemId())
                .party(DropDownDto.builder().key(party.getPartyId().toString())
                        .value(party.getFullName()).build())
                .auditDto(AuditDto.builder().lastModifiedBy(items.getLastModifiedBy())
                        .createdBy(items.getCreatedBy())
                        .lastModifiedDate(items.getLastModifiedDate())
                        .createdDate(items.getCreatedDate())
                        .build())
                .itemCode(items.getItemCode())
                .isActive(items.getIsActive())
                .totalQuantity(items.getTotalQuantity())
                .purchaseOrderLineItem(items.getPurchaseOrderItem()
                        .stream().map(this::convertPurchaseLideOrderDto).toList())
                .build();
    }

    private PurchaseOrderLineItemDto convertPurchaseLideOrderDto(PurchaseOrderItem lineItem) {
        return PurchaseOrderLineItemDto.builder()
                .purchaseOrderLineItemId(lineItem.getPurchaseOrderLineItemId())
                .s(lineItem.getS())
                .l(lineItem.getL())
                .xl(lineItem.getXl())
                .m(lineItem.getM())
                .xs(lineItem.getXs())
                .xxl(lineItem.getXxl())
                .xxxl(lineItem.getXxxl())
                .mixed(lineItem.getMixed())
                .details(lineItem.getDetails())
                .subTotal(lineItem.getSubTotal())
                .auditDto(AuditDto.builder().lastModifiedBy(lineItem.getLastModifiedBy())
                        .createdBy(lineItem.getCreatedBy())
                        .lastModifiedDate(lineItem.getLastModifiedDate())
                        .createdDate(lineItem.getCreatedDate())
                        .build())
                .build();

    }
}
