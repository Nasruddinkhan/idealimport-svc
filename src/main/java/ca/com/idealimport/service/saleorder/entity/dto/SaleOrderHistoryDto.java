package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.dto.AuditDto;
import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import ca.com.idealimport.service.saleorder.mapper.SaleOrderStatusEnumDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

@Builder
public record SaleOrderHistoryDto(AuditDto auditDto,
                                  @JsonDeserialize(using = SaleOrderStatusEnumDeserializer.class)
                                  SaleOrderStatusEnum orderStatus,
         String saleOrderId) {
}
