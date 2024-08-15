package ca.com.idealimport.service.saleorder.controller;

import ca.com.idealimport.common.dto.ApiResponse;
import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import ca.com.idealimport.config.security.SecureApi;
import ca.com.idealimport.service.saleorder.entity.dto.*;
import ca.com.idealimport.service.saleorder.service.SaleOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/sale-order/v1")
@RequiredArgsConstructor
@Slf4j
public class SaleOrderController implements SecureApi {
    private final SaleOrderService saleOrderService;

    @PostMapping
    public ResponseEntity<SaleOrderCreationResponse> createSaleOrder(@Valid @RequestBody SaleOrderRequestDto saleOrderRequest) {
        SaleOrderCreationResponse  saleOrderCreationResponse = saleOrderService.createSaleOrder(saleOrderRequest);
        if(saleOrderCreationResponse.status().getValue().equals(SaleOrderStatusEnum.CONFIRMED.getValue())){
            saleOrderService.updateInventory(saleOrderRequest.saleOrderId());
        }
        return new ResponseEntity<>(saleOrderCreationResponse, Objects.isNull(saleOrderRequest.saleOrderId()) ? HttpStatus.CREATED : HttpStatus.OK);
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

    @DeleteMapping("/{order-item}/item")
    @Deprecated
    public ResponseEntity<Void> deleteSaleOrderItem(@RequestParam(value = "order-amount-id", required = false) final String orderAmountId,
                                                   @PathVariable("order-item") final String oderItem) {
        saleOrderService.deleteSaleOrderItem(orderAmountId, oderItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{saleOrderId}")
    public ResponseEntity<Void> deleteBySaleOrderId(@PathVariable String saleOrderId){
        saleOrderService.deleteBySaleOrderId(saleOrderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateStatus(@RequestBody SaleOrderUpdateRequest  saleOrderUpdateRequest){
        return ResponseEntity.ok(saleOrderService.updateStatus(saleOrderUpdateRequest));
    }


}
