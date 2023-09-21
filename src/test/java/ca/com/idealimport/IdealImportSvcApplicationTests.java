package ca.com.idealimport;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class IdealImportSvcApplicationTests {

	@Test
	void contextLoads() {
		LocalDateTime localDateTime = LocalDateTime.parse("2023-08-14T08:46:59.330+00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
		System.out.println(localDateTime);
	}

}
