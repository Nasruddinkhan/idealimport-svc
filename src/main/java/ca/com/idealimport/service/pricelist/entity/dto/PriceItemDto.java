package ca.com.idealimport.service.pricelist.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceItemDto(Long priceId,
                           @JsonProperty("itemId") String itemCode,
                           @JsonProperty("price") BigDecimal amount) {
}
