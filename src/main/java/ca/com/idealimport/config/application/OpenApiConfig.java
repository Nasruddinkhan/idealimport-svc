package ca.com.idealimport.config.application;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("public")
//                .pathsToMatch("/actuator/**","/swagger-ui/**","/v3/api-docs",
//                        "/auth/v1.0/login",
//                        "/v3/api-docs/swagger-config")
//                .build();
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Your API Title").description("Your API Description"))
                .addSecurityItem(new SecurityRequirement().addList("ideal-api"))
                .components(new Components()
                        .addSecuritySchemes("ideal-api", new SecurityScheme()
                                .name("ideal-api")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                        .addParameters("Accept-Language", new Parameter()
                                .in("header")
                                .name("Accept-Language")
                                .description("Language header for internationalization")
                                .required(false)
                                .schema(new io.swagger.v3.oas.models.media.StringSchema())
                                .example("en-US")));
    }
}
