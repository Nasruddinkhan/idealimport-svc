package ca.com.idealimport.service.purchaseorder.entity.dto.response;

import ca.com.idealimport.common.dto.AuditDto;
import ca.com.idealimport.common.dto.DropDownDto;
import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseOrderItemsResponseDto(String purchaseOrderItemId,
                                            DropDownDto party,
                                            Boolean isActive,
                                            String itemCode,
                                            Integer totalQuantity,
                                            AuditDto auditDto,

                                            List<PurchaseOrderLineItemDto> purchaseOrderLineItem
) {
}
