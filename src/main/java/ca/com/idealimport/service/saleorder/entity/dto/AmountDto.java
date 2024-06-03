package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.dto.TaxDto;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AmountDto(
        String amountId,
        BigDecimal discount,
        BigDecimal subTotal,
        BigDecimal totalAmount,
        TaxDto tax,
        BigDecimal paid,
        Boolean isActive,
        BigDecimal balance) {
}
