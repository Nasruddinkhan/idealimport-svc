package ca.com.idealimport.common.util;

import java.util.Optional;
import java.util.UUID;

public class CommonUtils {
    private CommonUtils(){}

    public static String getUUID(String inputValue) {
        return Optional.ofNullable(inputValue)
                .orElseGet(()->UUID.randomUUID().toString());
    }
}
