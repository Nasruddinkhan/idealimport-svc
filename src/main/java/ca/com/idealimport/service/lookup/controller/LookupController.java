package ca.com.idealimport.service.lookup.controller;

import ca.com.idealimport.service.lookup.service.LookupService;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lookups/v1")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class LookupController {
    private final LookupService lookupService;

    @GetMapping("/items")
    public ResponseEntity<List<ItemPartyDto>> getItemCodeByPartyId(@RequestParam("party-id") Long partyId) {
        return ResponseEntity.ok(lookupService.getItemCodeByPartyId(partyId));
    }
}
