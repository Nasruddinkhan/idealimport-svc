package ca.com.idealimport.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConversionUtils {

    private ConversionUtils(){}

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convert Object to JSON string
    public static String objectToJson(Object object) throws IOException {
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

    // Convert JSON string to Object of specified class
    public static <T> T jsonToObject(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }

    // Convert Object to byte array
    public static byte[] objectToBytes(Object object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    // Convert byte array to Object of specified class
    public static <T> T bytesToObject(byte[] bytes, Class<T> valueType) throws IOException {
        return objectMapper.readValue(bytes, valueType);
    }

    // Convert List to JSON string
    public static String listToJson(List<?> list) throws IOException {
        return objectToJson(list);
    }

    // Convert JSON string to List of specified class
    public static <T> List<T> jsonToList(String json, Class<T> elementType) throws IOException {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
    }

    // Convert Object to JSON file
    public static void objectToJsonFile(Object object, File file) throws IOException {
        objectMapper.writeValue(file, object);
    }

    // Convert JSON file to Object of specified class
    public static <T> T jsonFileToObject(File file, Class<T> valueType) throws IOException {
        return objectMapper.readValue(file, valueType);
    }

    public static <T> List<T> jsonFileToList(File file, Class<T> valueType) throws IOException {
        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
    }

}
