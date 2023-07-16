package ca.com.idealimport.common.mapper;

import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductKey;
import ca.com.idealimport.service.product.entity.dto.ProductDTO;
import ca.com.idealimport.service.product.entity.dto.ProductResponseDto;
import ca.com.idealimport.service.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public ProductKey getProductKey(String uuId, Party party) {
        return ProductKey.builder()
                .productId(uuId)
                .party(party)
                .build();
    }

    public Product getProductDtoToProductEntity(ProductKey productKey, ProductDTO productDTO, User user) {
       return Product.builder()
               .productKey(productKey)
               .label(productDTO.label())
               .itemCode(productDTO.itemCode())
               .style(productDTO.style())
               .packingBox(productDTO.packingBox())
               .packingColors(productDTO.packingColors())
               .packingPolyBag(productDTO.packingPolyBag())
               .weight(productDTO.weight())
               .contents(productDTO.contents())
               .quantityInHand(productDTO.quantityInHand())
               .user(user)
               .isActive(Boolean.TRUE)
               .build();
    }


    public ProductResponseDto convertProductToProductResponse(Product product) {
        return ProductResponseDto.builder()
                .partyName(product.getProductKey().getParty().getFullName())
                .label(product.getLabel())
                .itemCode(product.getItemCode())
                .style(product.getStyle())
                .packingBox(product.getPackingBox())
                .packingColors(product.getPackingColors())
                .packingPolyBag(product.getPackingPolyBag())
                .weight(product.getWeight())
                .contents(product.getContents())
                .quantityInHand(product.getQuantityInHand())
                .productId(product.getProductKey().getProductId())
                .build();
    }
}
