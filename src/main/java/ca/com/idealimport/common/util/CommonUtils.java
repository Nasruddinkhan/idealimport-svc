package ca.com.idealimport.common.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

//    public static File readFileFromResources(String fileName,   ResourceLoader classLoader) throws IOException {
//     return   classLoader.getResource("classpath:"+ fileName).getFile();
//    }

    public static InputStream readFileFromResources(String filename, ResourceLoader resourceLoader) throws IOException {
        Resource resource = resourceLoader.getResource(filename);
        return resource.getInputStream();
    }
}
