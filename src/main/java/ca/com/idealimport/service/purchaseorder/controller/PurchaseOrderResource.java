package ca.com.idealimport.service.purchaseorder.controller;


import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
