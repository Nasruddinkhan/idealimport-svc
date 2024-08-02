package ca.com.idealimport.service.pricelist.entity.dto;


import ca.com.idealimport.common.dto.DropDownDto;
import lombok.Builder;

import java.util.List;

@Builder
public record CustomerPriceResponse(Long customerPartyId,
                                    DropDownDto customer,

                                    DropDownDto party,
                                    List<PriceItemDto> itemPrices) {

}
