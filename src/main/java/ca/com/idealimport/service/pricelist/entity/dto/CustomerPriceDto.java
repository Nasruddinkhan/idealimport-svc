package ca.com.idealimport.service.pricelist.entity.dto;

import java.util.List;

public record CustomerPriceDto(Long partyId, Long customerId, List<CustomerPriceDto> itemPrices) {
}
