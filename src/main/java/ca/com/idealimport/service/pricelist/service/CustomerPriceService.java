package ca.com.idealimport.service.pricelist.service;

import ca.com.idealimport.service.pricelist.entity.dto.CustomerPriceDto;
import ca.com.idealimport.service.pricelist.entity.dto.CustomerPriceResponse;
import org.springframework.data.domain.Page;

public interface CustomerPriceService {
    CustomerPriceResponse createCustomerPrice(CustomerPriceDto customerPriceDto);

    Page<CustomerPriceResponse> findAllCustomerPrice(int page, int size);

    void validatePrice(Long partyId, Long customerId);

    void deletePriceById(Long customerPartyId);
}
