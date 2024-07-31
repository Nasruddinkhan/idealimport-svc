package ca.com.idealimport.service.pricelist.entity.dto;

import java.math.BigDecimal;

public record PriceItemDto(String itemCode, BigDecimal amount) {
}
