package ca.com.idealimport.service.product.entity.dto;

import ca.com.idealimport.common.dto.AuditDto;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductResponseDto(String productId,
                                 String partyName,
                                 String itemCode,
                                 String contents,
                                 String style,
                                 String label,
                                 String weight,
                                 String packingPolyBag,
                                 String packingBox,
                                 String packingColors,
                                 AuditDto auditDto,
                                 Integer quantityInHand,
                                 List<ProductItemDTO> productItems,
                                 Boolean isEditable,
                                 Integer containerQuantity) {


}
