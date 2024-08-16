package ca.com.idealimport.service.saleorder.entity.dto;

import java.math.BigDecimal;

public record SaleOrderUpdateAmtRequest(String saleOrderId, String saleOrderAmtId, BigDecimal amount) {
}
