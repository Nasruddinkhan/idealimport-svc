package ca.com.idealimport.common.mapper;

import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.product.entity.dto.ProductCreationResponse;
import ca.com.idealimport.service.product.entity.dto.ProductItemDTO;
import ca.com.idealimport.service.users.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductItemMapper {
    public List<ProductItem> convertProductItemDtosToEntity(Product product, List<ProductItemDTO> productItemDTOS,  User user){
        return productItemDTOS.stream().map(e-> convertProductItemToEntity(product, e, user)).toList();
    }

    public ProductItem convertProductItemToEntity(Product product, ProductItemDTO productItemDTO, User user){

       return ProductItem.builder()
               .product(product)
               .productItemId(CommonUtils.getUUID(productItemDTO.productItemId()).toString())
               .s(productItemDTO.s())
               .l(productItemDTO.l())
               .xl(productItemDTO.xl())
               .m(productItemDTO.m())
               .xs(productItemDTO.xs())
               .xxl(productItemDTO.xxl())
               .xxxl(productItemDTO.xxxl())
               .mixed(productItemDTO.mixed())
               .details(productItemDTO.details())
               .user(user)
               .subTotal(productItemDTO.subTotal())
               .isActive(Boolean.TRUE)
               .build();

    }

    public ProductCreationResponse convertProductItemToProductCreationResponse(String productId) {
        return ProductCreationResponse.builder()
                .msg(String.format("create the product successfully with the product id %s", productId))
                .build();
    }

    public List<ProductItemDTO> convertProductItemEntityToDto(Product product){
        return product.getProductItems().stream().map(e-> convertProductItemToDto(e)).toList();
    }

    private ProductItemDTO convertProductItemToDto(ProductItem productItem) {
        return ProductItemDTO.builder()
                .productItemId(productItem.getProductItemId())
                .s(productItem.getS())
                .l(productItem.getL())
                .xl(productItem.getXl())
                .m(productItem.getM())
                .xs(productItem.getXs())
                .xxl(productItem.getXxl())
                .xxxl(productItem.getXxxl())
                .mixed(productItem.getMixed())
                .details(productItem.getDetails())
                .subTotal(productItem.getSubTotal())
                .build();

    }
}
