package ca.com.idealimport.service.purchaseorder.controller;


import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.SearchPurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderResponseDto;
import ca.com.idealimport.service.purchaseorder.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase-order/v1")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class PurchaseOrderResource {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrder(
            @RequestBody PurchaseOrderDto purchaseOrderDto) {
        log.info("PurchaseOrderResource.createPurchaseOrder start purchaseOrderDto={}", purchaseOrderDto);
        PurchaseOrderResponse response = purchaseOrderService.savePurchaseOrder(purchaseOrderDto);
        log.info("PurchaseOrderResource.createPurchaseOrder end response={}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<PurchaseOrderResponseDto>> getPurchaseOrder(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                                                           SearchPurchaseOrderDto searchProductDto) {
        log.info("ProductResource.createProduct getProducts");
        final var products = purchaseOrderService.getPurchaseOrder(page, size, searchProductDto);
        log.info("ProductResource.createProduct product={}", products);
        return ResponseEntity.ok(products);
    }
}
