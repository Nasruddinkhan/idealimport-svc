package ca.com.idealimport.service.product.entity.dto;

import lombok.Builder;

@Builder(builderClassName = "ProductBuilder")
public record ProductCreationResponse(String msg) {

}
