package ca.com.idealimport.service.product.entity.dto;

import lombok.Builder;

@Builder(builderClassName = "ProductItemBuilder")
public record ProductItemDTO(String productItemId,
                             String details,
                             Integer xs,
                             Integer l,
                             Integer xl,
                             Integer xxl,
                             Integer xxxl,
                             Integer mixed,
                             Integer subTotal,
                             Integer s,
                             Integer m) {

}
