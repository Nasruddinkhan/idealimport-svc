package ca.com.idealimport.service.product.control;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.party.control.PartyControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductControl {
    private final PartyControl partyControl;


    @Transactional(readOnly = true)
    public Page<DropDownDto> findAllParty(int page, int size, String fullName, Boolean isActive, String orderBy) {
        log.debug("ProductControl.findAllParty page ={} , size ={}, fullName ={}, isActive={}, orderBy ={}", page, size, fullName, isActive, orderBy);
        final var parties = partyControl.findAllParty(page, size, fullName, isActive, orderBy).map(e -> new DropDownDto(e.partyId().toString(), e.fullName()));
        log.debug("ProductControl.findAllParty  ={}", parties);
        return parties;

    }
}
