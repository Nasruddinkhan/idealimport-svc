package ca.com.idealimport.common.util;


import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.Optional;


@Converter
public class JsonListConverter<T> implements AttributeConverter<List<T>, String> {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final TypeReference<List<T>> typeReference;

    public JsonListConverter(TypeReference<List<T>> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        return Optional.ofNullable(attribute)
                .map(this::writeValueAsString)
                .orElse("[]");
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(this::readValue)
                .orElseGet(List::of);
    }

    private String writeValueAsString(List<T> data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, ex.getMessage());
        }
    }

    private List<T> readValue(String data) {
        try {
            return mapper.readValue(data, typeReference);
        } catch (Exception ex) {
            throw new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, ex.getMessage());
        }
    }
}
