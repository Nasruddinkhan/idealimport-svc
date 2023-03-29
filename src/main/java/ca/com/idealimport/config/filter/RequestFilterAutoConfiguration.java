package ca.com.idealimport.config.filter;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RequestFilterAutoConfiguration {
    private final IdealResponseBuilderFilter idealResponseBuilderFilter;

    @Bean
    public FilterRegistrationBean<Filter> responseBuilderFilter() {
        final FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.addUrlPatterns("/*");
        registrationBean.setFilter(idealResponseBuilderFilter);
        return registrationBean;
    }
}
