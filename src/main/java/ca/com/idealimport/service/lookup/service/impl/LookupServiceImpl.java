package ca.com.idealimport.service.lookup.service.impl;

import ca.com.idealimport.service.lookup.service.LookupService;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import ca.com.idealimport.service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupServiceImpl implements LookupService {
    private final ProductService productService;

    @Override
    public List<ItemPartyDto> getItemCodeByPartyId(Long partyId) {
        return productService.getItems(partyId);
    }
}
