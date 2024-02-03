package ca.com.idealimport.service.purchaseorder.entity.dto;

import ca.com.idealimport.service.purchaseorder.entity.enums.ShippingStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record AddPurchaseOrderDto(
        String addPurchaseOrderId,
        Long partyId,
        String itemCode,
        Integer totalQuantity,
        ShippingStatus status,
        List<AddPurchaseOrderItemDto> addPurchaseOrderItemDto
) {
}
