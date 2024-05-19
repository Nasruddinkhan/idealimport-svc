package ca.com.idealimport.service.saleorder.controller;

import ca.com.idealimport.config.security.SecureApi;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderRequestDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderResponse;
import ca.com.idealimport.service.saleorder.service.SaleOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale-order/v1")
@RequiredArgsConstructor
@Slf4j
public class SaleOrderController implements SecureApi {
    private final SaleOrderService saleOrderService;

    @PostMapping
    public ResponseEntity<SaleOrderResponse> createSaleOrder(@Valid @RequestBody SaleOrderRequestDto saleOrderRequest) {
        return new ResponseEntity<>(saleOrderService.createSaleOrder(saleOrderRequest), HttpStatus.CREATED);
    }
}
