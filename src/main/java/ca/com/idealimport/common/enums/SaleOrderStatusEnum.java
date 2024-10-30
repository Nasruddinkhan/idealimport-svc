package ca.com.idealimport.common.enums;

import java.util.Arrays;

public enum SaleOrderStatusEnum {
   // BACKORDERED("Backordered"),
    SHIPPED("Shipped"),
    PAID("Paid"),
    DELIVERED("Delivered"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold"),
    RETURNED("Returned"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    FAILED("Failed"),
    REFUNDED("Refunded"),
    PENDING_FOR_ADMIN("Pending For Admin"),
    PENDING("Pending"),
    OUT_FOR_DELIVERY("Out for Delivery");

    private final String value;

    SaleOrderStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SaleOrderStatusEnum fromValue(String value) {
        return Arrays.stream(SaleOrderStatusEnum.values())
                .filter(status -> status.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown value: " + value));
    }
}
