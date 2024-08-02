package ca.com.idealimport.service.pricelist.controller;

import ca.com.idealimport.service.pricelist.entity.dto.CustomerPriceDto;
import ca.com.idealimport.service.pricelist.entity.dto.CustomerPriceResponse;
import ca.com.idealimport.service.pricelist.service.CustomerPriceService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price/v1")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class PricingController {
    private final CustomerPriceService customerPriceService;

    @PostMapping
    public ResponseEntity<CustomerPriceResponse> addPriceForCustomer(@RequestBody CustomerPriceDto customerPriceDto) {
        final CustomerPriceResponse customerPrice = customerPriceService.createCustomerPrice(customerPriceDto);
        return new ResponseEntity<>(customerPrice, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerPriceResponse>> findAllCustomerPrice(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        final Page<CustomerPriceResponse> customerPriceResponses = customerPriceService.findAllCustomerPrice(page, size);
        return ResponseEntity.ok(customerPriceResponses);
    }

    @GetMapping("/validate-record")
    public ResponseEntity<CustomerPriceResponse> validatePrice(@RequestParam Long partyId,
                                                               @RequestParam Long customerId) {
        return ResponseEntity.ok(customerPriceService.validatePrice(partyId, customerId));
    }

}
