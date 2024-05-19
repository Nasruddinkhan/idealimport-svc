package ca.com.idealimport.service.saleorder.entity.dto;

import lombok.Builder;

@Builder
public record SaleOrderResponse(String name,
                                String msg,
                                String trackingId,
                                String status,
                                AmountDto amountDto,
                                long qty) {

}
