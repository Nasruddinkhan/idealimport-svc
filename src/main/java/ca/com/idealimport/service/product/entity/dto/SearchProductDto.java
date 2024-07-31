package ca.com.idealimport.service.product.entity.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchProductDto(Long partyId, String itemCode, String style, List<Long> parties) {
}
