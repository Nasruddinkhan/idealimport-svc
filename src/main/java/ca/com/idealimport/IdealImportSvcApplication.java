package ca.com.idealimport;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@SecurityScheme(
		name = "ideal-api",
		type = SecuritySchemeType.APIKEY,
		bearerFormat = "JWT",
		scheme = "Bearer",
		paramName = "Authorization",
		in = SecuritySchemeIn.HEADER
)
@EnableJpaAuditing
public class IdealImportSvcApplication {


	public static void main(String[] args) {
		SpringApplication.run(IdealImportSvcApplication.class, args);
	}


}
