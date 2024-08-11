package ca.com.idealimport.service.saleorder.controller;

import ca.com.idealimport.config.security.SecureApi;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderCreationResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderRequestDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderSearch;
import ca.com.idealimport.service.saleorder.service.SaleOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale-order/v1")
@RequiredArgsConstructor
@Slf4j
public class SaleOrderController implements SecureApi {
    private final SaleOrderService saleOrderService;

    @PostMapping
    public ResponseEntity<SaleOrderCreationResponse> createSaleOrder(@Valid @RequestBody SaleOrderRequestDto saleOrderRequest) {
        return new ResponseEntity<>(saleOrderService.createSaleOrder(saleOrderRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<Page<SaleOrderResponse>> findAllSaleOrder(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "10") int size,
                                                                    @RequestBody SaleOrderSearch saleOrderSearch) {
        return ResponseEntity.ok(saleOrderService.findAllSaleOrder(page, size, saleOrderSearch));
    }

    @GetMapping("/{tracking-id}")
    public ResponseEntity<SaleOrderResponse> findSaleOrderByTrackingId(@PathVariable("tracking-id") String trackingId) {
        return ResponseEntity.ok(saleOrderService.findSaleOrderByTrackingId(trackingId));
    }

    @DeleteMapping("/{order-amount-id}/{order-item}/item")
    public ResponseEntity<Void> deleteSaleOrderItem(@PathVariable("order-amount-id") final String orderAmountId,
                                                    @PathVariable("order-item") final String oderItem) {
        saleOrderService.deleteSaleOrderItem(orderAmountId, oderItem);
        return ResponseEntity.noContent().build();
    }
}
