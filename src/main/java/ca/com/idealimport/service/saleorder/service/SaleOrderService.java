package ca.com.idealimport.service.saleorder.service;

import ca.com.idealimport.common.dto.ApiResponse;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import ca.com.idealimport.service.saleorder.entity.dto.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface SaleOrderService {
    SaleOrderCreationResponse createSaleOrder(SaleOrderRequestDto saleOrderRequest);

    Page<SaleOrderResponse> findAllSaleOrder(int page, int size, SaleOrderSearch saleOrderSearch);

    SaleOrderResponse findSaleOrderByTrackingId(String trackingId);

    void deleteSaleOrderItem(String orderAmountId, String oderItem);

    void deleteBySaleOrderId(String saleOrderId);

    void updateInventory(String saleOrderId);

    ApiResponse updateStatus(SaleOrderUpdateRequest saleOrderUpdateRequest);

    ApiResponse updateAmount(SaleOrderUpdateAmtRequest updateAmtRequest);

    List<SaleOrderAmountHistoryDTO> findAllAmountHistory(String soOrderId, String amountHistory);
    SaleOrder getSaleOrder(String saleOrderId);

    List<SaleOrderHistoryDto> findAllSaleOrderHistory(String saleOrderId);

    List<SaleOrder> findBalanceByCustomer(Customer customer);
}
