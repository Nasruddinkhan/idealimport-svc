package ca.com.idealimport.service.saleorder.service;

import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderCreationResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderRequestDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderSearch;
import org.springframework.data.domain.Page;

public interface SaleOrderService {
    SaleOrderCreationResponse createSaleOrder(SaleOrderRequestDto saleOrderRequest);

    Page<SaleOrderResponse> findAllSaleOrder(int page, int size, SaleOrderSearch saleOrderSearch);

    SaleOrderResponse findSaleOrderByTrackingId(String trackingId);

    void deleteSaleOrderItem(String orderAmountId, String oderItem);

    void deleteBySaleOrderId(String saleOrderId);

    void updateInventory(String saleOrderId);
}
