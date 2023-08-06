package ca.com.idealimport.service.party.entity.dto;

import lombok.Builder;

@Builder
public record PartyDto(Long partyId,
                       String fullName,
                       String shortName) {

}