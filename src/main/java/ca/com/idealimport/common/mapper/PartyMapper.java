package ca.com.idealimport.common.mapper;

import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.party.entity.dto.PartyDto;
import ca.com.idealimport.service.users.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PartyMapper {
    public Party convertPartyDtoToParty(PartyDto partyDto, User loggedInUserId) {
        final var party = new Party();
        party.setPartyId(partyDto.partyId());
        party.setFullName(partyDto.fullName());
        party.setShortName(partyDto.shortName());
        party.setIsActive(Boolean.TRUE);
        party.setUser(loggedInUserId);
        return party;
    }

    public PartyDto convertPartyToPartyDto(Party party) {
        return PartyDto.builder()
                .fullName(party.getFullName())
                .shortName(party.getShortName())
                .partyId(party.getPartyId())
                .build();
    }

    public Party convertPartyDtoToParty(PartyDto partyDto, Party party) {
        party.setPartyId(partyDto.partyId());
        party.setFullName(partyDto.fullName());
        party.setShortName(partyDto.shortName());

        return party;
    }
}
