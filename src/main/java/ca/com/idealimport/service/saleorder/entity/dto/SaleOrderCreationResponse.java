package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import lombok.Builder;

@Builder
public record SaleOrderCreationResponse(String name,
                                String msg,
                                String trackingId,
                                SaleOrderStatusEnum status,
                                AmountDto amountDto,
                                long qty) {

}
