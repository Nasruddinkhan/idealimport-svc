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

import java.util.List;
import java.util.Map;

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

    @PostMapping("update-order-to-stock")
    public Map<String, String> movePurchaseOrderIntoProduct(@RequestBody List<String> purchaseOrderId) {
        return purchaseOrderService.movePurchaseOrderIntoProduct(purchaseOrderId);
    }

    @DeleteMapping("/{purchase-order-line-item}")
    public ResponseEntity<Void> deletePurchaseLineOrderId(@PathVariable("purchase-order-line-item") String purchaseOrderLineId) {
        log.info("PurchaseOrderResource.deletePurchaseLineOrderId purchaseOrderLineId ={}", purchaseOrderLineId);
        purchaseOrderService.deletePurchaseOrderItem(purchaseOrderLineId);
        log.info("PurchaseOrderResource.deletePurchaseLineOrderId end  purchaseOrderLineId={}", purchaseOrderLineId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{purchase-order-item-id}/{party-id}/order")
    public ResponseEntity<Void> deletePurchaseOrderId(@PathVariable("purchase-order-item-id") String purchaseOrderItemId,
                                                      @PathVariable("party-id") Long partyId) {
        log.info("PurchaseOrderResource.deletePurchaseOrderId purchaseOrderItemId ={}", purchaseOrderItemId);
        purchaseOrderService.deletePurchaseOrderId(purchaseOrderItemId, partyId);
        log.info("PurchaseOrderResource.deletePurchaseOrderId end  purchaseOrderLineId={}", purchaseOrderItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{purchase-order-id}/purchase-order")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable("purchase-order-id") String purchaseOrderId) {
        log.info("PurchaseOrderResource.deletePurchaseOrder purchaseOrderId ={}", purchaseOrderId);
        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
        log.info("PurchaseOrderResource.deletePurchaseOrderId end  purchaseOrderId={}", purchaseOrderId);
        return ResponseEntity.noContent().build();
    }
}
