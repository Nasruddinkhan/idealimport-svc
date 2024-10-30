package ca.com.idealimport.service.saleorder.mapper;

import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;

@JsonDeserialize(using = SaleOrderStatusEnumDeserializer.class)
public class SaleOrderStatusEnumDeserializer extends JsonDeserializer<SaleOrderStatusEnum> {
    @Override
    public SaleOrderStatusEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return SaleOrderStatusEnum.fromValue(value);
    }
}