package ca.com.idealimport.service.saleorder.entity.dto;


import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import ca.com.idealimport.service.saleorder.mapper.SaleOrderStatusEnumDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.util.List;

@Builder
public record SaleOrderRequestDto(String saleOrderId,
                                  DropDownDto customer,
                                  AmountDto amount,
                                  List<SaleOrderItemDto> items,
                                  @JsonDeserialize(using = SaleOrderStatusEnumDeserializer.class)
                                  SaleOrderStatusEnum orderStatus,
                                  SaleOrderInfoDto saleOrderInfo,

                                  String enableInvoice,
                                  String trackingId) {

}
