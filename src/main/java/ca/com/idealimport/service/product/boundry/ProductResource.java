package ca.com.idealimport.service.product.boundry;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.product.control.ProductControl;
import ca.com.idealimport.service.product.entity.dto.ProductCreationResponse;
import ca.com.idealimport.service.product.entity.dto.ProductDTO;
import ca.com.idealimport.service.product.entity.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class ProductResource {

    private final ProductControl productControl;
    @GetMapping("/party")
    public ResponseEntity<Page<DropDownDto>> findAllParty(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size,
                                                          @RequestParam(name = "full-name", required = false) String fullName,
                                                          @RequestParam(name = "order-by", required = false, defaultValue = "DESC") String orderBy,
                                                          @RequestParam(name = "active", required = false, defaultValue = "true") Boolean isActive){

        log.info("ProductResource.findAllParty start page ={}, size={}", page, size);
        final var dropDownDtos = productControl.findAllParty(page, size, fullName, isActive, orderBy);
        log.info("ProductResource.findAllParty end page ={}, size={}", dropDownDtos);
        return ResponseEntity.ok(dropDownDtos);

    }


    @PostMapping
    public ResponseEntity<ProductCreationResponse> createProduct(@RequestBody ProductDTO productDTO){
        log.info("ProductResource.createProduct product={}", productDTO);
        final var productCreationResponse = productControl.createProduct(productDTO);
        log.info("ProductResource.findAllParty end productCreationResponse ={}", productCreationResponse);
        return new ResponseEntity<>(productCreationResponse, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "10") int size){
        log.info("ProductResource.createProduct getProducts");
         var products =   productControl.getProducts( page,  size);
        log.info("ProductResource.createProduct product={}", products);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product-id/name")
    public ResponseEntity<ProductDTO> getProducts(@RequestParam("product-id") String productId,
                                                  @RequestParam("name") String fullName){
        log.info("ProductResource.getProducts productId={}, fullName={}", productId, fullName);
        final var  products =   productControl.findByProductById( productId,  fullName);
        log.info("ProductResource.getProducts product={}", products);
        return ResponseEntity.ok(products);
    }


}
