package ca.com.idealimport.service.auth.boundry;

import ca.com.idealimport.service.auth.control.AuthControl;
import ca.com.idealimport.service.auth.entity.dto.AuthRequestDto;
import ca.com.idealimport.service.auth.entity.dto.AuthResponseDto;
import ca.com.idealimport.service.users.control.UserControl;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1.0")
@Slf4j
public record AuthResource(AuthControl authControl) {

    /**
     *
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserDto userDto) {
        log.info("AuthResource.registerUser start {}", userDto);
        var userRegistrationResponse = authControl.registerUser(userDto);
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
