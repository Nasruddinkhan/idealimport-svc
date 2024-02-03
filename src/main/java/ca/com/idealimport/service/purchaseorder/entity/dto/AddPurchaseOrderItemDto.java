package ca.com.idealimport.service.purchaseorder.entity.dto;

import lombok.Builder;

@Builder
public record AddPurchaseOrderItemDto(
        String addPurchaseOrderItemId,
        Integer xs,
        Integer l,
        Integer xl,
        Integer xxl,
        Integer xxxl,
        Integer mixed,
        Integer subTotal,
        Integer s,
        Integer m,
        String details) {
}
