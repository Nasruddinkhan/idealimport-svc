package ca.com.idealimport.common.dto;

import lombok.Builder;

@Builder
public record DropDownDto(String key, String value) {
}
