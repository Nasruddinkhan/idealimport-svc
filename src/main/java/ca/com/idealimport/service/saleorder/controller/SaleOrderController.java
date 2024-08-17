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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @PutMapping("/update-amount")
    public ResponseEntity<ApiResponse> updateAmount(@RequestBody SaleOrderUpdateAmtRequest updateAmtRequest) {
        return ResponseEntity.ok(saleOrderService.updateAmount(updateAmtRequest));
    }

    @GetMapping("/amt-history")
    public ResponseEntity<List<SaleOrderAmountHistoryDTO>> findAllAmountHistory(
            @RequestParam("sale-order-id") String soOrderId,
            @RequestParam("amount-history-id") String amountHistory){
        return ResponseEntity.ok(saleOrderService.findAllAmountHistory(soOrderId, amountHistory));
    }

}
