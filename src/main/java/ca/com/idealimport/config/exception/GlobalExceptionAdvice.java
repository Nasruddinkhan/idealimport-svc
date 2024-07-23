package ca.com.idealimport.config.exception;

import ca.com.idealimport.common.dto.IdealErrorResponse;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.config.filter.builder.IdealResponseBuilder;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    private final IdealResponseBuilder idealResponseBuilder;
    private final MessageSource messageSource;


    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<IdealErrorResponse> handleIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException idealException, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse( new IdealException(IdealResponseErrorCode.DUPLICATE_RECORD, idealException.getMessage()), request, response);
    }
    @ExceptionHandler(value = {IdealException.class})
    public ResponseEntity<IdealErrorResponse> handleIdealException(IdealException idealException, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse(idealException, request, response);
    }

    @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
    public ResponseEntity<IdealErrorResponse> invalidDataAccessUsageException(InvalidDataAccessApiUsageException invalidDataAccessApiUsageException, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse(new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, invalidDataAccessApiUsageException.getMessage()), request, response);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<IdealErrorResponse> handleExpiredJwtException(ExpiredJwtException expiredJwtException, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse(new IdealException(IdealResponseErrorCode.TOKEN_EXPIRED), request, response);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<IdealErrorResponse> handleExpiredJwtException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        return idealResponseBuilder.createErrorResponse(new IdealException(IdealResponseErrorCode.UNEXPECTED_ERROR, exception.getMessage()), request, response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> error.getDefaultMessage(),
                        (oldValue, newValue) -> oldValue)); // In case of duplicate keys

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
