package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import ca.com.idealimport.service.customer.entity.dto.CustomerDto;
import lombok.Builder;

import java.util.List;

@Builder
public record SaleOrderResponse(SaleOrderInfoDto orderInfo,
                                List<SaleOrderItemDto> items,
                                CustomerDto customer,
                                List<AmountDto> amount,
                                SaleOrderStatusEnum orderStatus,
                                String saleOrderId,
                                String trackingId) {
}
