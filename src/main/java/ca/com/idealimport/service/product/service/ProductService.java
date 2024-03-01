package ca.com.idealimport.service.product.service;

import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;

import java.util.List;

public interface ProductService {
    List<ItemPartyDto> getItems(Long partyId);
}
