package ca.com.idealimport.service.lookup.service;

import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;

import java.util.List;

public interface LookupService {
    List<ItemPartyDto> getItemCodeByPartyId(Long partyId);
}
