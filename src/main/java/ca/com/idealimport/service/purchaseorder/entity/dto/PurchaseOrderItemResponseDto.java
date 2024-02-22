package ca.com.idealimport.service.purchaseorder.entity.dto;

import lombok.Builder;

@Builder
public record PurchaseOrderItemResponseDto(
                                           String purchaseOrderItemId,
                                           String details,
                                           int xs,
                                           int s,
                                           int m,
                                           int l,
                                           int xl,
                                           int xxl,
                                           int xxxl,
                                           int mixed,
                                           int subTotal,
                                           boolean isActive
) {
}
