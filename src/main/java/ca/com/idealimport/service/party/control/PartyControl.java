package ca.com.idealimport.service.party.control;

import ca.com.idealimport.common.mapper.PartyMapper;
import ca.com.idealimport.common.pagination.CommonPageable;
import ca.com.idealimport.common.specifications.Specifications;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.party.boundry.PartyRepository;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.party.entity.dto.PartyDto;
import ca.com.idealimport.service.users.control.UserControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class PartyControl {
    private final PartyMapper partyMapper;
    private final UserControl userControl;
    private final PartyRepository partyRepository;

    private final PageUtils pageUtils;

    public PartyDto createParty(PartyDto partyDto) {
        log.debug("PartyControl.createParty start partyDto ={}", partyDto);
        final var party = Optional.ofNullable(partyDto)
                .map(e -> partyMapper.convertPartyDtoToParty(e, userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId())))
                .map(partyRepository::save)
                .map(partyMapper::convertPartyToPartyDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, "party object cannot be empty"));
        log.debug("PartyControl.createParty end party ={}", party);
        return party;
    }


    public PartyDto updateParty(PartyDto partyDto) {
        log.debug("PartyControl.updateParty start partyDto ={}", partyDto);
        final var party = partyRepository.findById(partyDto.partyId())
                .map(e -> partyMapper.convertPartyDtoToParty(partyDto, e))
                .map(partyRepository::saveAndFlush)
                .map(partyMapper::convertPartyToPartyDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format("Party record not present", partyDto.fullName())));
        log.debug("PartyControl.updateParty end party ={}", party);
        return party;
    }

    public Page<PartyDto> findAllParty(int page, int size, String fullName) {
        Specification<Party> specification = Specification.where(null);
        log.debug("PartyControl.findAllParty start page ={}, size = {}", page, size);
        if (fullName != null) {
            specification = specification.and(Specifications.fieldContains("fullName", fullName));
        }
        final var partyPage = pageUtils.getPageableOrder(page, size, "desc", "partyId");
        final var partyDto = partyRepository.findAll( specification, partyPage)
                .map(partyMapper::convertPartyToPartyDto);
        log.debug("PartyControl.findAllParty start partyDto ={}", partyDto);
        return partyDto;
    }
}
