package ca.com.idealimport;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		name = "ideal-api",
		type = SecuritySchemeType.APIKEY,
		bearerFormat = "JWT",
		scheme = "Bearer",
		paramName = "Authorization",
		in = SecuritySchemeIn.HEADER
)
public class IdealImportSvcApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(IdealImportSvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
