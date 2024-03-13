package ca.com.idealimport.service.product.service.impl;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.mapper.ProductItemMapper;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.boundry.repository.ProductRepository;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import ca.com.idealimport.service.product.service.ProductService;
import ca.com.idealimport.service.productitem.boundry.ProductItemRepository;
import ca.com.idealimport.service.purchaseorder.entity.dto.UpdatePurchaseOrderBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final PartyControl partyControl;
    private final ProductRepository productRepository;
    private final ProductItemMapper productItemMapper;
    private final ProductItemRepository itemRepository;

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

    @Override
    @Transactional
    public void updateProductStock(UpdatePurchaseOrderBean orderBean) {
        Product product = productRepository.findByProductKeyPartyAndItemCode(orderBean.party(), orderBean.itemCode());
        product.getProductItems().stream()
                .filter(e -> e.getDetails().equals(orderBean.orderItem().getColor()))
                .findAny()
                .map(e -> productItemMapper.mapPurchaseOrderItenToProductItem(e, orderBean))
                .map(itemRepository::save)
                .get();

        Integer sum = product.getProductItems().stream().map(ProductItem::getSubTotal).reduce(0, Integer::sum);
        product.setQuantityInHand(sum);
        product.setIsEditable(false);
        productRepository.save(product);
        log.info(String.format("%s stock update successfully with %s quantity with in hand",
                product.getItemCode(), product.getQuantityInHand()));

    }
}
