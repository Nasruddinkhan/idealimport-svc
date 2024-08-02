package ca.com.idealimport.service.pricelist.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CustomerPriceDto(Long customerPartyId,
                               Long partyId,
                               Long customerId,
                               @JsonProperty("items") List<PriceItemDto> itemPrices) {
}
