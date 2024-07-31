package ca.com.idealimport.service.pricelist.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price/v1")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class PricingController {

    public void addPriceForCustomer() {

    }
}
