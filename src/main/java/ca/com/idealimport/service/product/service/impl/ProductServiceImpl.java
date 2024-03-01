package ca.com.idealimport.service.product.service.impl;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.boundry.repository.ProductRepository;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import ca.com.idealimport.service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final PartyControl partyControl;
    private final ProductRepository productRepository;

    @Override
    public List<ItemPartyDto> getItems(Long partyId) {
        Party party = partyControl.findParty(partyId);
        return productRepository.findByProductKeyParty(party)
                .stream().map(e -> ItemPartyDto.builder()
                        .items(DropDownDto.builder()
                                .key(e.getItemCode())
                                .value(e.getItemCode())
                                .build())
                        .details(e.getProductItems().stream()
                                .map(ProductItem::getDetails)
                                .map(dtl -> DropDownDto.builder()
                                        .key(dtl)
                                        .value(dtl)
                                        .build())
                                .toList())
                        .build()).toList();
    }
}
