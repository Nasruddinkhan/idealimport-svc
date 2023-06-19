package ca.com.idealimport;

import ca.com.idealimport.service.users.control.UserControl;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.xml.crypto.Data;
import java.util.Date;

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
public class IdealImportSvcApplication implements CommandLineRunner {

	//@Autowired
	//private UserControl userControl;
	public static void main(String[] args) {
		SpringApplication.run(IdealImportSvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//var users  = userControl.findAllUser(0, 10);
		//System.out.println("userControl users ="+ users.get().toList().size());
	}
}
