package ca.com.idealimport.service.saleorder.entity.dto;


import ca.com.idealimport.common.dto.DropDownDto;
import lombok.Builder;

import java.util.List;

@Builder
public record SaleOrderRequestDto(String saleOrderId,
                                  DropDownDto customer,
                                  AmountDto amount,
                                  List<SaleOrderItemDto> items,
                                  DropDownDto orderStatus,
                                  SaleOrderInfoDto saleOrderInfo,
                                  String trackingId) {

}
