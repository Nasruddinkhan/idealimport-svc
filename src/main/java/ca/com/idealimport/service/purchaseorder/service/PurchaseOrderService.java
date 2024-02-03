package ca.com.idealimport.service.purchaseorder.service;

import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;

public interface PurchaseOrderService {
    PurchaseOrderResponse savePurchaseOrder(PurchaseOrderDto orderDto);
}
