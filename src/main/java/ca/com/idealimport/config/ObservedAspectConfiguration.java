package ca.com.idealimport.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ServerHttpObservationFilter;

@Configuration
@Slf4j
public class ObservedAspectConfiguration {
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        log.info("ObservedAspectConfiguration.observedAspect {} ", observationRegistry);
        return new ObservedAspect(observationRegistry);
    }

    @ConditionalOnBean(ObservationRegistry.class)
    @ConditionalOnMissingBean(ServerHttpObservationFilter.class)
    @Bean
    public ServerHttpObservationFilter observationFilter(ObservationRegistry registry) {
        log.info("ObservedAspectConfiguration.observationFilter {} ", registry);
        return new ServerHttpObservationFilter(registry);
    }
}
