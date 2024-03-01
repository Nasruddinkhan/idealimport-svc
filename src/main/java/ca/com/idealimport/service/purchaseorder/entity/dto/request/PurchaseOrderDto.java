package ca.com.idealimport.service.purchaseorder.entity.dto.request;

import ca.com.idealimport.service.purchaseorder.entity.dto.AddPurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.enums.ShippingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PurchaseOrderDto(
        String purchaseOrderId,
        String lotNumber,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate orderDate,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate departureDate,
        String containerName,
        ShippingStatus status,
        Integer totalQuantity,
        List<AddPurchaseOrderDto> addPurchaseOrderDto
) {
}
