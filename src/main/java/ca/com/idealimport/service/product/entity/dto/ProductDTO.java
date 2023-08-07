package ca.com.idealimport.service.product.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder(builderClassName = "ProductBuilder")
public record ProductDTO(List<ProductItemDTO> productItems,
                         String productId,
                         Long partyId,
                         String itemCode,
                         String contents,
                         String style,
                         String label,
                         String weight,
                         String packingPolyBag,
                         String packingBox,
                         String packingColors,

                         @JsonProperty("quantityInhand") Integer quantityInHand) {

  
}
