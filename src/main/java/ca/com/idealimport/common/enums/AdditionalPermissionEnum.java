package ca.com.idealimport.common.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AdditionalPermissionEnum {
    INVENTORY_MANAGER_FULL_ACCESS,
    INVENTORY_VIEW_ACCESS,
    SALE_ORDER_FULL_ACCESS,
    SALE_ORDER_READ_ACCESS,
    PO_FULL_ACCESS,
    PO_READ_ACCESS;

    // Method to convert enum to key-value pairs with custom names
    public static Map<String, String> getAdditionalPermission() {
        return Stream.of(AdditionalPermissionEnum.values())
                .collect(Collectors.toMap(
                        AdditionalPermissionEnum::name,
                        AdditionalPermissionEnum::getCustomName
                ));
    }

    // Helper method to provide custom display names
    private static String getCustomName(AdditionalPermissionEnum permission) {
        return switch (permission) {
            case INVENTORY_MANAGER_FULL_ACCESS -> "Inventory Manager - Full Access";
            case INVENTORY_VIEW_ACCESS -> "Inventory - View Access";
            case SALE_ORDER_FULL_ACCESS -> "Sale Order - Full Access";
            case SALE_ORDER_READ_ACCESS -> "Sale Order - Read Access";
            case PO_FULL_ACCESS -> "Purchase Order - Full Access";
            case PO_READ_ACCESS -> "Purchase Order - Read Access";
        };
    }

    public static void main(String[] args) {
        Map<String, String> permissionMap = AdditionalPermissionEnum.getAdditionalPermission();
        System.out.println(permissionMap);
    }
}

