package ca.com.idealimport.service.purchaseorder.service;

import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponseDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.SearchPurchaseOrderDto;
import org.springframework.data.domain.Page;

public interface PurchaseOrderService {
    PurchaseOrderResponse savePurchaseOrder(PurchaseOrderDto orderDto);

    Page<PurchaseOrderResponseDto> getPurchaseOrder(int page, int size, SearchPurchaseOrderDto searchProductDto);
}
