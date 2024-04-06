package ca.com.idealimport.service.saleorder.controller;

import ca.com.idealimport.config.security.SecureApi;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponseDto;
import ca.com.idealimport.service.saleorder.service.SaleOrderStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sale-order-status/v1")
@RequiredArgsConstructor
@Slf4j

public class SaleOrderStatusController implements SecureApi {

    private final SaleOrderStatusService saleOrderStatusService;

    @PostMapping
    public ResponseEntity<SaleOrderStatusDto> createStatus(@Valid @RequestBody SaleOrderStatusDto saleOrderStatusDto) {
        return new ResponseEntity<>(saleOrderStatusService.createStatus(saleOrderStatusDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SaleOrderStatusResponseDto>> findAllSaleOrders() {
        return ResponseEntity.ok(saleOrderStatusService.findAllStatus());
    }

}
