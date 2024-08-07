package ca.com.idealimport.service.party.control;

import ca.com.idealimport.common.Constants;
import ca.com.idealimport.common.constants.ErrorConstants;
import ca.com.idealimport.common.mapper.PartyMapper;
import ca.com.idealimport.common.specifications.SpecificationUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        final var party = Optional.ofNullable(partyDto).map(e -> partyMapper.convertPartyDtoToParty(e, userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId()))).map(partyRepository::save).map(partyMapper::convertPartyToPartyDto).orElseThrow(() -> new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, "party object cannot be empty"));
        log.debug("PartyControl.createParty end party ={}", party);
        return party;
    }


    public PartyDto updateParty(PartyDto partyDto) {
        log.debug("PartyControl.updateParty start partyDto ={}", partyDto);
        final var party = partyRepository.findById(partyDto.partyId()).map(e -> partyMapper.convertPartyDtoToParty(partyDto, e)).map(partyRepository::saveAndFlush).map(partyMapper::convertPartyToPartyDto).orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format("Party record not present %s", partyDto.fullName())));
        log.debug("PartyControl.updateParty end party ={}", party);
        return party;
    }

    public Page<PartyDto> findAllParty(int page, int size, String fullName, Boolean isActive, String orderBy) {
        log.debug("PartyControl.findAllParty start page ={}, size = {}", page, size);
        final var specification = buildWhereConditions(fullName, isActive);
        final var partyPage = pageUtils.getPageableOrder(page, size, orderBy, Constants.PARTY_ID);
        final var partyDto = partyRepository.findAll(specification, partyPage).map(partyMapper::convertPartyToPartyDto);
        log.debug("PartyControl.findAllParty start partyDto ={}", partyDto);
        return partyDto;
    }

    public List<Party> findAllParty() {
        return partyRepository.findAll();
    }



    private Specification<Party> buildWhereConditions(String fullName, Boolean isActive) {
        final List<Specification<Party>> specificationsList = new ArrayList<>();
        specificationsList.add(Specifications.fieldProperty(Constants.ACTIVE, isActive));
        Optional.ofNullable(fullName)
                .ifPresent(name -> specificationsList.add(Specifications.fieldContains(Constants.FULL_NAME, name)));
        return SpecificationUtils.and(specificationsList);
    }

    public PartyDto findPartyById(Long partyId) {
        log.debug("PartyControl.findPartyById start partyId = {}", partyId);
        final var partyDto = partyRepository.findByPartyIdAndIsActiveTrue(partyId)
                .map(partyMapper::convertPartyToPartyDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format(ErrorConstants.PARTY_NOT_PRESENT, partyId)));
        log.debug("PartyControl.findPartyById end partyDto = {}", partyDto);
        return partyDto;
    }

    public PartyDto deletePartyById(Long partyId) {
        log.debug("PartyControl.deletePartyById start partyId = {}", partyId);
        final var partyDto = partyRepository.findByPartyIdAndIsActiveTrue(partyId)
                .map(partyMapper::setInActive)
                .map(partyRepository::save)
                .map(partyMapper::convertPartyToPartyDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format(ErrorConstants.PARTY_NOT_PRESENT, partyId)));
        log.debug("PartyControl.deletePartyById end partyDto = {}", partyDto);
        return partyDto;
    }

    public Party findParty(Long partyId) {
        return  partyRepository.findByPartyIdAndIsActiveTrue(partyId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format(ErrorConstants.PARTY_NOT_PRESENT, partyId)));
    }

    public Party findParty(String name){
        return  partyRepository.findByFullNameAndIsActiveTrue(name)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format(ErrorConstants.PARTY_NAME_NOT_PRESENT, name)));
    }
}
