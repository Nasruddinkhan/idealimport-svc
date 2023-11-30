package ca.com.idealimport.service.purchaseorder.entity.dto;

import ca.com.idealimport.service.purchaseorder.entity.enums.ShippingStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record AddPurchaseOrderDto(
        String addPurchaseOrderId,
        String partyName,
        String itemName,
        double totalQuantity,
        ShippingStatus status,
        List<AddPurchaseOrderItemDto> addPurchaseOrderItemDto
) {
}
