package ca.com.idealimport.config.audit;

import ca.com.idealimport.common.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AuditorAwareUser implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.getLoggedInUserId()).orElse("System").describeConstable();
    }
}
