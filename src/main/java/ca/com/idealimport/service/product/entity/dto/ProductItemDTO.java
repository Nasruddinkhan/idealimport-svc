package ca.com.idealimport.service.product.entity.dto;

import lombok.Builder;

import java.math.BigDecimal;

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
                             Integer m,
                             BigDecimal unitPrice) {

    public ProductItemDTO withUnitPrice(BigDecimal newUnitPrice) {
        return new ProductItemDTO(
                this.productItemId,
                this.details,
                this.xs,
                this.l,
                this.xl,
                this.xxl,
                this.xxxl,
                this.mixed,
                this.subTotal,
                this.s,
                this.m,
                newUnitPrice
        );
    }
}
