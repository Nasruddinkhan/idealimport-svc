package ca.com.idealimport.common.mapper;

import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.product.entity.dto.ProductCreationResponse;
import ca.com.idealimport.service.product.entity.dto.ProductItemDTO;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItem;
import ca.com.idealimport.service.purchaseorder.entity.dto.UpdatePurchaseOrderBean;
import ca.com.idealimport.service.users.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductItemMapper {
    public List<ProductItem> convertProductItemDtosToEntity(Product product, List<ProductItemDTO> productItemDTOS,  User user){
        return productItemDTOS.stream().map(e-> convertProductItemToEntity(product, e, user)).toList();
    }

    public ProductItem convertProductItemToEntity(Product product, ProductItemDTO productItemDTO, User user){

       return ProductItem.builder()
               .product(product)
               .productItemId(CommonUtils.getUUID(productItemDTO.productItemId()))
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
        return product.getProductItems().stream().map(this::convertProductItemToDto).toList();
    }

    public ProductItemDTO convertProductItemToDto(ProductItem productItem) {
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
    public ProductItemDTO convertProductItemToDto(ProductItem productItem, BigDecimal unitPrice) {
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
                .unitPrice(unitPrice)
                .unitPrice(unitPrice)
                .build();

    }


    public ProductItem mapPurchaseOrderItenToProductItem(ProductItem productItem, UpdatePurchaseOrderBean orderBean) {
        PurchaseOrderItem item = orderBean.orderItem();
        productItem.setL(productItem.getL() + item.getL());
        productItem.setM(productItem.getM() + item.getM());
        productItem.setXl(productItem.getXl() + item.getXl());
        productItem.setS(productItem.getS() + item.getS());
        productItem.setMixed(productItem.getMixed() + item.getMixed());
        productItem.setSubTotal(productItem.getSubTotal() + item.getSubTotal());
        productItem.setXxl(productItem.getXxl() + item.getXxl());
        productItem.setXxxl(productItem.getXxxl() + item.getXxxl());
        productItem.setXs(productItem.getXs() + item.getXs());
        return productItem;
    }
}
