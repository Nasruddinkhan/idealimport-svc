package ca.com.idealimport.config.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
public interface SecureApi {
}
