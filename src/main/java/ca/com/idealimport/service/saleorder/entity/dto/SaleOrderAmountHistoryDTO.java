package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.dto.AuditDto;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaleOrderAmountHistoryDTO(BigDecimal paidAmount, BigDecimal remainingAmount, AuditDto auditDto) {
}
