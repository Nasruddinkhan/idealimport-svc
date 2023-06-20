package ca.com.idealimport.config.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RequestResponseLoggingAspect {

    private final ObjectMapper objectMapper;


    @Pointcut("@annotation(LogRequestResponse)")
    public void logRequestResponsePointcut() {
    }

    @Before("logRequestResponsePointcut()")
    public void logRequest(JoinPoint joinPoint) throws JsonProcessingException {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object request = args[0];
            log.info("Request: {}", objectMapper.writeValueAsString(request));
        }
    }

    @AfterReturning(pointcut = "logRequestResponsePointcut()", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) throws JsonProcessingException {
        log.info("Response: {}", objectMapper.writeValueAsString(response));
    }
}