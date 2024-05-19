package ca.com.idealimport.service.saleorder.entity.dto;

import ca.com.idealimport.common.dto.DropDownDto;
import lombok.Builder;


@Builder
public record SaleOrderItemDto(
        String saleOrderItemId,
        String itemCode,
        DropDownDto party,
        OrderItemDto orderItem) {

}
