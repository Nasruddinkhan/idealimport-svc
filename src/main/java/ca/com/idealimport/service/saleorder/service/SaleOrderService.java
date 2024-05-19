package ca.com.idealimport.service.saleorder.service;

import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderRequestDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderResponse;

public interface SaleOrderService {
    SaleOrderResponse createSaleOrder(SaleOrderRequestDto saleOrderRequest);
}
