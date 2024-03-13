package ca.com.idealimport.service.purchaseorder.service;

import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.SearchPurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface PurchaseOrderService {
    PurchaseOrderResponse savePurchaseOrder(PurchaseOrderDto orderDto);

    Page<PurchaseOrderResponseDto> getPurchaseOrder(int page, int size, SearchPurchaseOrderDto searchProductDto);

    Map<String, String> movePurchaseOrderIntoProduct(List<String> purchaseOrderId);

    void deletePurchaseOrderItem(String purchaseOrderId);

    void deletePurchaseOrderId(String purchaseOrderItemId, Long partyId);

    void deletePurchaseOrder(String purchaseOrderId);

}
