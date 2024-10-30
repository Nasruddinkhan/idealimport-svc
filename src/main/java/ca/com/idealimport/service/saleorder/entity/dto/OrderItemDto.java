package ca.com.idealimport.service.saleorder.entity.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemDto(
        String orderItemId,
        String productItemId,
        String color,
        Integer xs,
        Integer l,
        Integer xl,
        Integer xxl,
        Integer xxxl,
        Integer mixed,
        Integer subTotal,
        BigDecimal unitPrice,
        Integer s,
        Integer m,
        Integer qty) {
}
