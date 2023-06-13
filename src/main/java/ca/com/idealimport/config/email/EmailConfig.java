package ca.com.idealimport.config.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

@Configuration
public class EmailConfig {
    @Bean
    public SpringTemplateEngine getSpringTemplateEngine() {
        final var templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(getHtmlTemplateResolver());
        return templateEngine;
    }
    @Bean
    public SpringResourceTemplateResolver getHtmlTemplateResolver(){
        final var emailTemplateResolver = new SpringResourceTemplateResolver();
        emailTemplateResolver.setPrefix("classpath:/templates/html/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return emailTemplateResolver;
    }
}
