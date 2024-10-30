package ca.com.idealimport.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ApiTimeAspect {

    @Around("@annotation(LogApiTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime(); // Start the timer with nanoTime for higher precision
        try {
            Object proceed = joinPoint.proceed();
            long executionTime = System.nanoTime() - start; // Calculate execution time in nanoseconds
            log.info(joinPoint.getSignature() + " executed in " + (executionTime / 1_000_000_000.0) + " seconds"); // Convert to seconds
            return proceed;
        } catch (Exception e) {
            long executionTime = System.nanoTime() - start; // Calculate execution time in nanoseconds
            log.error(joinPoint.getSignature() + " threw an exception in " + (executionTime / 1_000_000_000.0) + " seconds"); // Convert to seconds
            log.error("Exception: " + e.getMessage());
            throw e;
        }
    }
}
