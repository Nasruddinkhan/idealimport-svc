package ca.com.idealimport.service.productitem.boundry;

import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.product.entity.dto.ProductItemDTO;
import ca.com.idealimport.service.productitem.control.ProductItemControl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product-item/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class ProductItemResource {

    private final ProductItemControl productItemControl;

    @DeleteMapping("/{product-id}")
    public ResponseEntity<Void> deleteProductItemById(@PathVariable("product-id") String productId) {
        log.info("ProductItemResource.deleteByProductItem productId={}", productId);
        productItemControl.deleteProductItemById(productId);
        log.info("ProductItemResource.deleteByProductItem");
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ProductItemDTO> getProductItem(
            @RequestParam("party-id") Long partyId,
            @RequestParam("item-code") String itemCode,
            @RequestParam("color") String color) {
        return ResponseEntity.ok(productItemControl.getProductItem(partyId, itemCode, color));
    }

    @GetMapping("/product-item-id")
    public ResponseEntity<ProductItemDTO> getProductItem(
            @RequestParam("id") String productItemId) {
        ProductItem item = productItemControl.findProductItemById(productItemId);

        return ResponseEntity.ok(ProductItemDTO.builder()
                .productItemId(item.getProductItemId())
                .details(item.getDetails())
                .l(item.getL())
                .m(item.getM())
                .xl(item.getXl())
                .xs(item.getXs())
                .s(item.getS())
                .xxl(item.getXxl())
                .xxxl(item.getXxxl())
                .mixed(item.getMixed())
                .subTotal(item.getSubTotal())
                .build());
    }

    @GetMapping("/item")
    public ResponseEntity<List<ProductItemDTO>> getProductItem(
            @RequestParam("party-id") Long partyId,
            @RequestParam("item-code") String itemCode,
            @RequestParam(name = "customerId", required = false) Long customerId) {
        return ResponseEntity.ok(productItemControl.getProductItem(partyId, itemCode, customerId));
    }

}
