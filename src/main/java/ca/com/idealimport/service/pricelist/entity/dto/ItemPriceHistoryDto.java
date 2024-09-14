package ca.com.idealimport.service.pricelist.entity.dto;

import ca.com.idealimport.common.dto.AuditDto;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemPriceHistoryDto(String customerName,
                                  String itemId,
                                  String pStyle,
                                  String style,
                                  BigDecimal price,
                                  String partyName,
                                  AuditDto auditDto) {


}
