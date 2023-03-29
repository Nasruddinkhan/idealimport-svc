package ca.com.idealimport.service.users.boundry;

import ca.com.idealimport.service.users.control.UserControl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
public record UserResource(UserControl userControl) {


}
