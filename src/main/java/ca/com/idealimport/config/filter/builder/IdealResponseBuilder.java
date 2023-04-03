package ca.com.idealimport.config.filter.builder;

import ca.com.idealimport.common.dto.IdealError;
import ca.com.idealimport.common.dto.IdealErrorResponse;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class IdealResponseBuilder {

    public ResponseEntity<IdealErrorResponse> createErrorResponse(IdealException idealException, HttpServletRequest request,
                                                                  HttpServletResponse response) {
        log.info("Start IdealResponseBuilder.createErrorResponse ");
        var errorReply = createIdealError(idealException, request);
        log.info("Start IdealResponseBuilder.createErrorResponse before add response header ");
        addResponseHeaders(request, response);
        log.info("Start IdealResponseBuilder.createErrorResponse after add response header ");
        var responseEntityHeaders = new HttpHeaders();

        var headerNames = (List<String>) response.getHeaderNames();
        if (!CollectionUtils.isEmpty(headerNames)) {
            headerNames.forEach(headerName -> {
                responseEntityHeaders.set(headerName, response.getHeader(headerName));
                log.info("Start IdealResponseBuilder.createErrorResponse add set headers " + headerName);
            });
        }
        log.info("Start IdealResponseBuilder.createErrorResponse after set responseEntityHeaders ");
        log.info("Start IdealResponseBuilder.createErrorResponse set status " + idealException.getError().getHttpStatus());
        log.info("Start IdealResponseBuilder.createErrorResponse set body " + errorReply);
        log.info("Start IdealResponseBuilder.createErrorResponse set responseEntityHeaders " + responseEntityHeaders);
        return ResponseEntity.status(Optional.ofNullable(HttpStatus.resolve(idealException.getError().getHttpStatus())).orElse(HttpStatus.BAD_REQUEST))
                .headers(responseEntityHeaders).body(errorReply);
    }

    private IdealErrorResponse createIdealError(IdealException idealException, HttpServletRequest request) {
        var error = idealException.getError();
        var msg = idealException.getMsg();
        var errorId = idealException.getId();
        if (error == null) {
            error = IdealResponseErrorCode.UNEXPECTED_ERROR;
        }
        msg = StringUtils.isBlank(msg) ? error.getMsg() : msg;
        var status = Optional.ofNullable(HttpStatus.resolve(error.getHttpStatus())).orElse(HttpStatus.BAD_REQUEST);
        var errorList = List.of(new IdealError(error.getCode(), msg, request.getContextPath(), request.getRequestURI()));
        return new IdealErrorResponse(status.toString(), errorId, error.getMsg(), errorList);
    }

    public void addResponseHeaders(HttpServletRequest request, HttpServletResponse response) {
        var interactionId = request.getHeader("interaction-id");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("interaction-id", Optional.ofNullable(interactionId).orElse(UUID.randomUUID().toString())); // later will change with thread local
        //later add correlation Id
    }
}
