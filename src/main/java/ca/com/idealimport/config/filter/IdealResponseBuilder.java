package ca.com.idealimport.config.filter;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
            for (String headerName : headerNames) {
                responseEntityHeaders.set(headerName, response.getHeader(headerName));
                log.info("Start IdealResponseBuilder.createErrorResponse add set headers " + headerName);
            }
        }
        log.info("Start IdealResponseBuilder.createErrorResponse after set responseEntityHeaders ");
        log.info("Start IdealResponseBuilder.createErrorResponse set status " + idealException.getError().getHttpStatus());
        log.info("Start IdealResponseBuilder.createErrorResponse set body " + errorReply);
        log.info("Start IdealResponseBuilder.createErrorResponse set responseEntityHeaders " + responseEntityHeaders);
        return ResponseEntity.status(HttpStatus.resolve(idealException.getError().getHttpStatus()))
                .headers(responseEntityHeaders).body(errorReply);
    }

    private IdealErrorResponse createIdealError(IdealException idealException, HttpServletRequest request) {
        var idealError = idealException.getError();
        var msg = idealException.getMsg();
        var errorId = idealException.getId();
        if (idealError == null) {
            idealError = IdealResponseErrorCode.UNEXPECTED_ERROR;
        }
        if (StringUtils.isBlank(msg)) {
            msg = idealError.getMsg();
        }
        return new IdealErrorResponse(HttpStatus.resolve(idealError.getHttpStatus()).toString(), errorId, msg, List.of(new IdealError(idealError.getCode(), msg, request.getPathInfo(), request.getRequestURI())));

    }

    private void addResponseHeaders(HttpServletRequest request, HttpServletResponse response) {
    }
}
