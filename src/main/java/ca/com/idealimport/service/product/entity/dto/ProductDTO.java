package ca.com.idealimport.service.product.entity.dto;

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
                         Integer quantityInHand) {

    @java.beans.ConstructorProperties({"productItems", "productId", "partyId", "itemCode", "contents", "style", "label", "weight", "packingPolyBag", "packingBox", "packingColors","quantityInHand"})
    public ProductDTO{

    }
}
