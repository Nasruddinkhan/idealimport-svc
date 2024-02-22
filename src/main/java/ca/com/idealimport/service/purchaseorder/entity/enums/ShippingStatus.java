package ca.com.idealimport.service.purchaseorder.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShippingStatus {
    ARRIVED, //2 System object
    IN_TRANSIT,// 1
    APPROVED // --- ?
}
