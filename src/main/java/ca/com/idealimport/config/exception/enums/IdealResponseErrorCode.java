package ca.com.idealimport.config.exception.enums;

import lombok.Getter;

@Getter
public enum IdealResponseErrorCode {

    INVALID_PERMISSION("Passing Invalid Permission", 400, "Passing invalid permission, kindly check & pass valid parameter"),
    NOT_FOUND("Not Found", 404, "Not Found ."),
    UNEXPECTED_ERROR("Unexceptional Error ",500 ,"Unexceptional Error ");
    private final String code;
    private final int httpStatus;
    private final String msg;

    IdealResponseErrorCode(String code, int httpStatus, String msg) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
