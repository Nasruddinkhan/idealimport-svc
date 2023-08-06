package ca.com.idealimport.service.product.entity.dto;

import lombok.Builder;

@Builder
public record ProductResponseDto(String productId,
                                 String partyName,
                                 String itemCode,
                                 String contents,
                                 String style,
                                 String label,
                                 String weight,
                                 String packingPolyBag,
                                 String packingBox,
                                 String packingColors,
                                 Integer quantityInHand) {


}
