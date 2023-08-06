package ca.com.idealimport.service.product.entity.dto;

import lombok.Builder;

@Builder
public record SearchProductDto(Long partyId, String itemCode, String style) {
}
