package ca.com.idealimport.service.lookup.service.impl;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.customer.control.CustomerControl;
import ca.com.idealimport.service.lookup.service.LookupService;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import ca.com.idealimport.service.product.service.ProductService;
import ca.com.idealimport.service.saleorder.service.SaleOrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupServiceImpl implements LookupService {
    private final ProductService productService;
    private final CustomerControl customerControl;
    private final SaleOrderStatusService statusService;
    @Override
    public List<ItemPartyDto> getItemCodeByPartyId(Long partyId) {
        return productService.getItems(partyId);
    }

    @Override
    public Page<DropDownDto> findAllCustomer(int page, int size, String fullName, Boolean isActive, String orderBy) {
        return customerControl.findAllCustomer(page, size).map(e -> DropDownDto.builder()
                .key(String.valueOf(e.customerId()))
                .value(String.format("%s - %s", e.customerName(), e.companyName()))
                .build());
    }

    @Override
    public List<DropDownDto> findAllSaleOrderStatus() {
        return statusService.findAllStatus()
                .stream()
                .map(e -> DropDownDto.builder().key(e.statusDto().saleOrderStatusId())
                        .value(e.statusDto().name())
                        .build())
                .toList();
    }
}
