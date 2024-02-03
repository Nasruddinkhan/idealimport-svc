package ca.com.idealimport.service.purchaseorder.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record PurchaseOrderItemResponseDto(String createdBy,
                                           @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)

                                           Date createdDate,
                                           String lastModifiedBy,
                                           @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)

                                           Date lastModifiedDate,
                                           String purchaseOrderItemId,
                                           String details,
                                           int xs,
                                           int s,
                                           int m,
                                           int l,
                                           int xl,
                                           int xxl,
                                           int xxxl,
                                           int mixed,
                                           int subTotal,
                                           boolean isActive
) {
}
