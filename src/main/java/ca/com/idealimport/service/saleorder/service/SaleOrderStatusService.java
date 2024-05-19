package ca.com.idealimport.service.saleorder.service;

import ca.com.idealimport.service.saleorder.entity.SaleOrderStatus;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderStatusResponseDto;

import java.util.List;

public interface SaleOrderStatusService {
    SaleOrderStatusDto createStatus(SaleOrderStatusDto saleOrderStatusDto);

    List<SaleOrderStatusResponseDto> findAllStatus();

    SaleOrderStatus findStatus(String statusId);

}
