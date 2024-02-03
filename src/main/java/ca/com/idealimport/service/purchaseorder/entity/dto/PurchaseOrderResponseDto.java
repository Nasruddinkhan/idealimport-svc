package ca.com.idealimport.service.purchaseorder.entity.dto;

import ca.com.idealimport.service.purchaseorder.entity.enums.ShippingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PurchaseOrderResponseDto(String lotNumber,
                                       String containerName,
                                       @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
                                       LocalDate orderDate,
                                       @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
                                       LocalDate departureDate,
                                       ShippingStatus shippingStatus,
                                       boolean isActive,
                                       List<PurchaseOrderItemResponseDto> purchaseOrderItems,
                                       String itemName,
                                       int totalQuantity) {
}
