package ca.com.idealimport.service.auth.boundry;

import ca.com.idealimport.common.mapper.EmailMapper;
import ca.com.idealimport.service.auth.control.AuthControl;
import ca.com.idealimport.service.auth.entity.dto.AuthRequestDto;
import ca.com.idealimport.service.auth.entity.dto.AuthResponseDto;
import ca.com.idealimport.service.mail.control.MailSenderService;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth/v1.0")
@Slf4j
public class AuthResource {

    private final AuthControl authControl;
    private final String registrationTemplateName;
    private final EmailMapper emailMapper;
    private final MailSenderService mailSenderService;
    public AuthResource(AuthControl authControl, @Value("${mail.login-credential}") String registrationTemplateName, EmailMapper emailMapper, MailSenderService mailSenderService) {
        this.authControl = authControl;
        this.registrationTemplateName = registrationTemplateName;
        this.emailMapper = emailMapper;
        this.mailSenderService = mailSenderService;
    }

    /**
     *
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserDto userDto) {
        log.info("AuthResource.registerUser start {}", userDto);
        String password = UUID.randomUUID().toString();
        var userRegistrationResponse = authControl.registerUser(userDto, password);
        mailSenderService.sendEmail(emailMapper.convertRegisterUserDtoToMailDto(userDto, password, userRegistrationResponse.userName()), registrationTemplateName);
        log.info("AuthResource.registerUser end {}", userRegistrationResponse);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authRequest(@RequestBody AuthRequestDto authRequestDto) {
        log.info("AuthResource.authRequest start {}", authRequestDto);
        var userRegistrationResponse = authControl.authRequest(authRequestDto);
        log.info("AuthResource.authRequest end {}", userRegistrationResponse);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<AuthResponseDto> regenerateToken() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("AuthResource.regenerateToken start {}", userName);
        var userRegistrationResponse = authControl.authRequest(userName);
        log.info("AuthResource.regenerateToken end {}", userRegistrationResponse);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.OK);
    }


}
