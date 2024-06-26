package ca.com.idealimport.service.productitem.control;

import ca.com.idealimport.common.constants.ErrorConstants;
import ca.com.idealimport.common.mapper.ProductItemMapper;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.boundry.repository.ProductRepository;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.product.entity.dto.ProductItemDTO;
import ca.com.idealimport.service.productitem.boundry.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductItemControl {
    private final ProductItemRepository productItemRepository;
    private final PartyControl partyControl;
    private final ProductRepository productRepository;
    private final ProductItemMapper productItemMapper;

    public void deleteProductItemById(String productItemId) {
        productItemRepository.deleteById(productItemId);
    }

    public ProductItemDTO getProductItem(Long partyId, String itemCode, String color) {
        Party party = partyControl.findParty(partyId);
        Product product = productRepository.findByProductKeyPartyAndItemCode(party, itemCode);
        return productItemRepository.findByDetailsAndProduct(color, product)
                .map(productItemMapper::convertProductItemToDto)
                .orElseThrow(() ->
                        new IdealException(IdealResponseErrorCode.NOT_FOUND,
                                String.format(ErrorConstants.PRODUCT_ITEM_NOT_FOUND, itemCode, color))
                );

    }

    public List<ProductItemDTO> getProductItem(Long partyId, String itemCode) {
        Party party = partyControl.findParty(partyId);
        Product product = productRepository.findByProductKeyPartyAndItemCode(party, itemCode);
        return product.getProductItems()
                .stream()
                .map(productItemMapper::convertProductItemToDto)
                .toList();

    }


    public ProductItem findProductItemById(String productItemId) {
        return productItemRepository.findById(productItemId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format(ErrorConstants.PRODUCT_ORDER_LINE_NOT_PRESENT, productItemId)));
    }


}
