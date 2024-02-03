package ca.com.idealimport.service.purchaseorder.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShippingStatus {
    ARRIVED,
    IN_TRANSIT,
    APPROVED
}
