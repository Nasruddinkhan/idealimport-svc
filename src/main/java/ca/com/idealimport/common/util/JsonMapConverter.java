package ca.com.idealimport.common.util;

import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Converter
public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String> {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }


    @Override
    public String convertToDatabaseColumn(Map<String, Object> data) {

        return Optional.ofNullable(data)
                .map(JsonMapConverter::getConvertToDatabase)
                .orElseGet(() -> "{}");

    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        return Optional.ofNullable(s)
                .map(JsonMapConverter::getConvertToDatabase)
                .orElseGet(() -> new HashMap<>());

    }

    private static String getConvertToDatabase(Map<String, Object> data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, ex.getMessage());
        }
    }

    private static Map<String, Object> getConvertToDatabase(String data) {
        try {
            return mapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, ex.getMessage());
        }
    }
}