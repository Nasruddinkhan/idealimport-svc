package ca.com.idealimport.config.filter.builder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class IdealResponseBuilderFilter extends OncePerRequestFilter {
    private final IdealResponseBuilder idealResponseBuilder;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        idealResponseBuilder.addResponseHeaders(request, response);
        filterChain.doFilter(request, response);

    }
}
