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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tax/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class TaxResource {
    private final TaxService taxService;
    @PostMapping
    public ResponseEntity<TaxDto> createTax(@RequestBody TaxDto taxDto){
        log.info("TaxResource.createTax tax start {}", taxDto);
        final var tax = taxService.createTax(taxDto);
        log.info("TaxResource.createTax end taxDto {}", tax);
        return new ResponseEntity<>(tax, HttpStatus.CREATED);
    }

    @GetMapping("/{tax-Id}")
    public ResponseEntity<TaxDto> findTaxById(@PathVariable("tax-Id") String taxId){
        log.info("TaxResource.findTaxById taxId start {}", taxId);
        final var tax = taxService.findTaxById(taxId);
        log.info("TaxResource.findTaxById end taxDto {}", tax);
        return ResponseEntity.ok(tax);
    }

    @GetMapping
    public ResponseEntity<List<TaxDto>> findAllTaxes(){
        log.info("TaxResource.findAllTaxes  start {}");
        final var tax = taxService.findAllTaxes();
        log.info("TaxResource.findAllTaxes end tax {}", tax);
        return ResponseEntity.ok(tax);
    }

    @DeleteMapping("/{tax-Id}")
    public ResponseEntity<Void> deleteTaxByTaxId(@PathVariable("tax-Id") String taxId){
        log.info("TaxResource.deleteTaxByTaxId  start {}");
        taxService.deleteTaxByTaxId(taxId);
        log.info("TaxResource.deleteTaxByTaxId end");
        return ResponseEntity.noContent().build();
    }
}
