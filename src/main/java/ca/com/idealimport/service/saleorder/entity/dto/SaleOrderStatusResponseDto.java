package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.dto.AuditDto;
import lombok.Builder;

@Builder
public record SaleOrderStatusResponseDto(SaleOrderStatusDto statusDto, AuditDto auditDto) {
}
