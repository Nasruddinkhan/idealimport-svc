package ca.com.idealimport.service.lookup.service;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.product.entity.dto.ItemPartyDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LookupService {
    List<ItemPartyDto> getItemCodeByPartyId(Long partyId);

    Page<DropDownDto> findAllCustomer(int page, int size, String fullName, Boolean isActive, String orderBy);
}
