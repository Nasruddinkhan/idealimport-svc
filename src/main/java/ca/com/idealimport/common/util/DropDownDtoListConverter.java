package ca.com.idealimport.common.util;


import ca.com.idealimport.common.dto.DropDownDto;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.Converter;

@Converter
public class DropDownDtoListConverter extends JsonListConverter<DropDownDto> {
    public DropDownDtoListConverter() {
        super(new TypeReference<>() {
        });
    }
}