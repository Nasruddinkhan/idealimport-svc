package ca.com.idealimport.service.purchaseorder.entity.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AddPurchaseOrderDto(
        String addPurchaseOrderId,
        Long partyId,
        String itemCode,
        Integer totalQuantity,
        List<AddPurchaseOrderItemDto> addPurchaseOrderItemDto
) {
}
