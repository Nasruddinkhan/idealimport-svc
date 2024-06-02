package ca.com.idealimport.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class CommonUtils {

    private CommonUtils() {
    }

    public static String getUUID(String inputValue) {
        return Optional.ofNullable(inputValue)
                .orElseGet(() -> UUID.randomUUID().toString());
    }

    public static int safeValue(Integer value) {
        return value == null ? 0 : value;
    }

    public static File readFileFromResources(String fileName,   ClassLoader classLoader) throws IOException {
      return new File(classLoader.getResource(fileName).getFile());
    }
}
