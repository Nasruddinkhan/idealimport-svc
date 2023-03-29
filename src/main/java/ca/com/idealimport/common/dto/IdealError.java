package ca.com.idealimport.common.dto;

import java.io.Serializable;

public record IdealError(String errorCode, String message, String path, String url) implements Serializable {
}
