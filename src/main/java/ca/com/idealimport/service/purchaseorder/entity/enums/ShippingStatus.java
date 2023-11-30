package ca.com.idealimport.service.purchaseorder.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShippingStatus {

    ARRIVED("Arrived"),
    IN_TRANSIT("InTransit"),
    APPROVED("Approved");

    private final String status;

}
