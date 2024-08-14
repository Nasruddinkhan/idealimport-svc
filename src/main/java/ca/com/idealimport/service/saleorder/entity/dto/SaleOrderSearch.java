package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import ca.com.idealimport.service.saleorder.mapper.SaleOrderStatusEnumDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

@Builder
public record SaleOrderSearch(@JsonDeserialize(using = SaleOrderStatusEnumDeserializer.class)
                              SaleOrderStatusEnum status, String saleOrderNo, String trackingNo) {
}
