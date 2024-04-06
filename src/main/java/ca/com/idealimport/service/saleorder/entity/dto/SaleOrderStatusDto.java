package ca.com.idealimport.service.saleorder.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static ca.com.idealimport.common.constants.MessageConstants.DESCRIPTION_NOT_BLANK;
import static ca.com.idealimport.common.constants.MessageConstants.FIELD_VLD_SIZE;
import static ca.com.idealimport.common.constants.MessageConstants.SALE_ORDER_NAME_NOT_BLANK;

@Builder
public record SaleOrderStatusDto(String saleOrderStatusId,
                                 @NotBlank(message = SALE_ORDER_NAME_NOT_BLANK)
                                 @Size(message = FIELD_VLD_SIZE, min = 4, max = 60)
                                 String name,
                                 @NotBlank(message = DESCRIPTION_NOT_BLANK)
                                 @Size(message = FIELD_VLD_SIZE, min = 5, max = 4000)
                                 String description) {
}
