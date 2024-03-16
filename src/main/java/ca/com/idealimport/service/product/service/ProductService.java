package ca.com.idealimport.service.product.service;

import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.UpdatePurchaseOrderBean;

import java.util.List;

public interface ProductService {
    List<ItemPartyDto> getItems(Long partyId);

    void updateProductStock(UpdatePurchaseOrderBean orderBean);

    void deleteProduct(String productId);
}
