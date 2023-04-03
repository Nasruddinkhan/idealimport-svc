package ca.com.idealimport.config.filter;

import ca.com.idealimport.config.filter.builder.IdealResponseBuilderFilter;
import ca.com.idealimport.config.filter.validator.IdealRequestValidatorFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RequestFilterAutoConfiguration {
    private final IdealResponseBuilderFilter idealResponseBuilderFilter;
    private final IdealRequestValidatorFilter idealRequestValidatorFilter;

    @Bean
    public FilterRegistrationBean<Filter> responseBuilderFilter() {
        final FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);

        registrationBean.setFilter(idealResponseBuilderFilter);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> requestValidatorFilter() {
        final FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setFilter(idealRequestValidatorFilter);
        return registrationBean;
    }
}
