package ca.com.idealimport.service.product.service.impl;

import ca.com.idealimport.common.constants.ErrorConstants;
import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.mapper.ProductItemMapper;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.boundry.repository.ProductRepository;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import ca.com.idealimport.service.product.service.ProductService;
import ca.com.idealimport.service.productitem.boundry.ProductItemRepository;
import ca.com.idealimport.service.purchaseorder.entity.dto.UpdatePurchaseOrderBean;
import ca.com.idealimport.service.purchaseorder.repository.PurchaseOrderItemsRepository;
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
    private final PurchaseOrderItemsRepository orderItemsRepository;

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

    @Override
    @Transactional
    public void deleteProduct(String productId) {
        Product product = productRepository
                .findByProductKeyProductIdAndIsActiveTrue(productId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND,
                        String.format(ErrorConstants.PRODUCT_NOT_PRESENT, productId)));
        var products = orderItemsRepository
                .findByPurchaseOrderItemIdKeyPartyAndItemCode(product.getProductKey().getParty(), product.getItemCode());
        if (products != null && products.size() > 0) {
            throw new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, ErrorConstants.PRODUCT_NOT_DELETE);
        }
        productRepository.deleteById(product.getProductKey());
        //more function way
        /**
         productRepository.findByProductKeyProductIdAndIsActiveTrue(productId)
         .ifPresentOrElse(product -> {
         orderItemsRepository
         .findByPurchaseOrderItemIdKeyPartyAndItemCode(product.getProductKey().getParty(), product.getItemCode())
         .ifPresent(orderItem -> {
         throw new IdealException(
         IdealResponseErrorCode.UNEXPECTED_ERROR,
         ErrorConstants.PRODUCT_NOT_DELETE
         );
         });
         },
         () -> {
         throw new IdealException(IdealResponseErrorCode.NOT_FOUND,
         String.format(ErrorConstants.PRODUCT_NOT_PRESENT, productId));
         });
         **/
    }
}
