package ca.com.idealimport.service.purchaseorder.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)

public record AddPurchaseOrderDto(
        String addPurchaseOrderId,
        Long partyId,
        String itemCode,
        Integer totalQuantity,
        List<AddPurchaseOrderItemDto> addPurchaseOrderItemDto
) {
}
