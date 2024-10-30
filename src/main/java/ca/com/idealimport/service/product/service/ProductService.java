package ca.com.idealimport.service.product.service;

import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.UpdatePurchaseOrderBean;

import java.util.List;

public interface ProductService {
    List<ItemPartyDto> getItems(Long partyId);

    void updateProductStock(UpdatePurchaseOrderBean orderBean);

    void deleteProduct(String productId);

    Product findByProductKeyPartyAndItemCode(Party party, String itemCode);
}
