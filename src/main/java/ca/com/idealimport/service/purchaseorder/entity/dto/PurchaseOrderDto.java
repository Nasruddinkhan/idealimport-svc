package ca.com.idealimport.service.purchaseorder.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PurchaseOrderDto(
        String purchaseOrderId,
        String lotNumber,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate orderDate,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate departureDate,
        String containerName,
        AddPurchaseOrderDto addPurchaseOrderDto
) {
}
