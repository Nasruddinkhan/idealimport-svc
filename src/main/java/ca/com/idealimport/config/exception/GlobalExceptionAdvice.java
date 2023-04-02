package ca.com.idealimport.config.exception;

import ca.com.idealimport.common.dto.IdealErrorResponse;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.config.filter.builder.IdealResponseBuilder;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    private final IdealResponseBuilder idealResponseBuilder;
    @ExceptionHandler(value = {IdealUnknownException.class})
    public ResponseEntity<IdealErrorResponse> handleException(Exception idealException, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse( new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, idealException.getMessage()), request, response);
    }
    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class, MySQLIntegrityConstraintViolationException.class})
    public ResponseEntity<IdealErrorResponse> handleIntegrityConstraintViolationException(MySQLIntegrityConstraintViolationException idealException, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse( new IdealException(IdealResponseErrorCode.DUPLICATE_RECORD, idealException.getMessage()), request, response);
    }
    @ExceptionHandler(value = {IdealException.class})
    public ResponseEntity<IdealErrorResponse> handleIdealException(IdealException idealException, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse(idealException, request, response);
    }
}
