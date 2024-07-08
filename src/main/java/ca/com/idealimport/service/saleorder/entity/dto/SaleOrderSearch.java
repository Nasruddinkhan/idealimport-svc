package ca.com.idealimport.service.saleorder.entity.dto;

import lombok.Builder;

@Builder
public record SaleOrderSearch(String status, String saleOrderNo, String trackingNo) {
}
