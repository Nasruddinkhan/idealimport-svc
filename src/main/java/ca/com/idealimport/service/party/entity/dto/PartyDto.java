package ca.com.idealimport.service.party.entity.dto;

import lombok.Builder;


public record PartyDto(Long partyId,
                       String fullName,
                       String shortName) {
    @Builder
    public PartyDto {
        // throw new UnsupportedOperationException();
    }
}