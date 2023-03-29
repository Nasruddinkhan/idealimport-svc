package ca.com.idealimport.common.dto;

import java.util.List;

public record IdealErrorResponse(String code, String id, String message, List<IdealError> errors) {
}
