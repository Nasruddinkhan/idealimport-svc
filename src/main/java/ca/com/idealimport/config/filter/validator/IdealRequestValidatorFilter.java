package ca.com.idealimport.config.filter.validator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class IdealRequestValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("IdealRequestValidatorFilter.doFilterInternal start");
        var host = request.getHeader("Host");
        var url = request.getRequestURL().toString();
        if (!url.contains(host)) throw new ServletException("Bad host header provide request will be rejected");
        filterChain.doFilter(request, response);
        log.debug("IdealRequestValidatorFilter.doFilterInternal end");
    }
}
