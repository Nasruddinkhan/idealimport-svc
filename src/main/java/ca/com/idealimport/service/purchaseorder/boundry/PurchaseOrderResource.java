package ca.com.idealimport.service.purchaseorder.boundry;


import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
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
@RequestMapping("/purchase-order/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class PurchaseOrderResource {
    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrder(
            @RequestBody PurchaseOrderDto purchaseOrderDto){
        log.info("PurchaseOrderResource.createPurchaseOrder purchaseOrderDto={}", purchaseOrderDto);
        return new ResponseEntity<>(PurchaseOrderResponse.builder()
                .msg("create purchase order successfully")
                .build(), HttpStatus.CREATED);
    }
}
