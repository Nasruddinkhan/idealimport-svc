package ca.com.idealimport.service.saleorder.entity.dto;

import lombok.Builder;

@Builder
public record SaleOrderInfoDto(
        String saleOrderInfoId,
        String orderBy,
        String orderNo,
        String via,
        String ref,
        String remarks,
        String conditions) {
}
