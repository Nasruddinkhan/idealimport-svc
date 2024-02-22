package ca.com.idealimport.service.purchaseorder.service;

import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.SearchPurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderResponseDto;
import org.springframework.data.domain.Page;

public interface PurchaseOrderService {
    PurchaseOrderResponse savePurchaseOrder(PurchaseOrderDto orderDto);

    Page<PurchaseOrderResponseDto> getPurchaseOrder(int page, int size, SearchPurchaseOrderDto searchProductDto);

}
