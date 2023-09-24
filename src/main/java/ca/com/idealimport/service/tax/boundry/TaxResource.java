package ca.com.idealimport.service.tax.boundry;

import ca.com.idealimport.common.dto.TaxDto;
import ca.com.idealimport.service.tax.boundry.repository.TaxRepository;
import ca.com.idealimport.service.tax.service.TaxService;
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
@RequestMapping("/tax/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class TaxResource {
    private final TaxService taxService;
    private final TaxRepository taxRepository;
    @PostMapping
    public ResponseEntity<TaxDto> createTax(@RequestBody TaxDto taxDto){
        log.info("TaxResource.createTax tax start {}", taxDto);
        final var tax = taxService.createTax(taxDto);
        log.info("TaxResource.createTax end taxDto {}", tax);
        return new ResponseEntity<>(tax, HttpStatus.CREATED);
    }

}
