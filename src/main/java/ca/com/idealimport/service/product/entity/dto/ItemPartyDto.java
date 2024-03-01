package ca.com.idealimport.service.product.entity.dto;

import ca.com.idealimport.common.dto.DropDownDto;
import lombok.Builder;

import java.util.List;

@Builder
public record ItemPartyDto(DropDownDto items, List<DropDownDto> details) {
}
