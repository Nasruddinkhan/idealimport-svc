package ca.com.idealimport.service.purchaseorder.entity.dto.response;

import ca.com.idealimport.common.dto.AuditDto;
import lombok.Builder;

@Builder
public record PurchaseOrderLineItemDto(String purchaseOrderLineItemId,
                                       String details,
                                       Integer xs,
                                       Integer l,
                                       Integer xl,
                                       Integer xxl,
                                       Integer xxxl,
                                       Integer mixed,
                                       Integer subTotal,
                                       Integer s,
                                       Integer m,
                                       AuditDto auditDto) {
}
