package ca.com.idealimport.service.party.boundry;

import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.dto.PartyDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/party/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class PartyResource {

    private final PartyControl control;

    @PostMapping
    public ResponseEntity<PartyDto> createParty(@RequestBody PartyDto partyDto) {
        log.info("PartyResource.createParty start partyDto ={}", partyDto);
        final var party = control.createParty(partyDto);
        log.info("PartyResource.createParty end partyDto ={}", partyDto);
        return new ResponseEntity<>(party, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PartyDto> updateParty(@RequestBody PartyDto partyDto) {
        log.info("PartyResource.updateParty start partyDto ={}", partyDto);
        final var party = control.updateParty(partyDto);
        log.info("PartyResource.updateParty end partyDto ={}", partyDto);
        return ResponseEntity.ok(party);
    }

    @GetMapping
    public ResponseEntity<Page<PartyDto>> findAllParty(@RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                                       @RequestParam(name = "full-name", required = false) String fullName,
                                                       @RequestParam(name = "order-by", required = false, defaultValue = "DESC") String orderBy,
                                                       @RequestParam(name = "active", required = false, defaultValue = "true") Boolean isActive) {
        log.info("PartyResource.findAllParty start page ={}, size={}", page, size);
        final var party = control.findAllParty(page, size, fullName, isActive, orderBy);
        log.info("PartyResource.findAllParty end page ={}, size={}", party);
        return ResponseEntity.ok(party);
    }

    @GetMapping("/{party-id}/party-id")
    public ResponseEntity<PartyDto> findPartyById(@PathVariable("party-id") String partyId) {
        log.info("PartyResource.findPartyById start partyId ={}", partyId);
        final var party = control.findPartyById(partyId);
        log.info("PartyResource.findPartyById end  party={}", party);
        return ResponseEntity.ok(party);
    }

    @DeleteMapping("/{party-id}")
    public ResponseEntity<Void> deletePartyById(@PathVariable("party-id") String partyId) {
        log.info("PartyResource.deletePartyById partyId ={}", partyId);
        final var party = control.deletePartyById(partyId);
        log.info("PartyResource.deletePartyById end  party={}", party);
        return ResponseEntity.noContent().build();
    }
}
