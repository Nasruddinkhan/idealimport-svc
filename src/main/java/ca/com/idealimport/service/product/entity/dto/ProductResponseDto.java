package ca.com.idealimport.service.product.entity.dto;

import ca.com.idealimport.service.party.entity.dto.PartyDto;
import lombok.Builder;

@Builder
public record ProductResponseDto(String productId,
                                 PartyDto party,
                                 String itemCode,
                                 String contents,
                                 String style,
                                 String label,
                                 String weight,
                                 String packingPolyBag,
                                 String packingBox,
                                 String packingColors,
                                 Integer quantityInHand) {

    @java.beans.ConstructorProperties({"productId", "party", "itemCode", "contents", "style", "label", "weight", "packingPolyBag", "packingBox", "packingColors", "quantityInHand"})
    public ProductResponseDto {

    }
}
