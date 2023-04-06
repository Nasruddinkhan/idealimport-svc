package ca.com.idealimport.service.auth.control;

import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.auth.entity.dto.AuthRequestDto;
import ca.com.idealimport.service.auth.entity.dto.AuthResponseDto;
import ca.com.idealimport.config.jwt.JwtService;
import ca.com.idealimport.service.token.control.TokenControl;
import ca.com.idealimport.service.users.control.UserControl;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.stream.Stream;

@Service
@Slf4j
public record AuthControl(UserControl userControl, AuthenticationManager authenticationManager,
                          ObjectMapper objectMapper,
                          JwtService jwtService,
                          TokenControl tokenControl) {


    /**
     * @param userDto
     * @return
     */

    public UserRegistrationResponse registerUser(@RequestBody UserDto userDto) {
        log.info("AuthControl.registerUser start {}", userDto);
        var userRegistrationResponse = userControl.createUser(userDto);
        log.info("AuthControl.registerUser end {}", userRegistrationResponse);
        return userRegistrationResponse;
    }

    public AuthResponseDto authRequest(AuthRequestDto authRequestDto) {
        var authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.userName(), authRequestDto.password()));
      /*
       var userObj = Stream.of(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.userName(), authRequestDto.password())))
               .map(Authentication::getName)
               .map(userControl::findUserByEmailOrId).findFirst()
               .orElseThrow(()-> new IdealException(IdealResponseErrorCode.BAD_CREDENTIAL));
       */

        var user = userControl.findUserByEmailOrId(authenticate.getName());
        var claims = objectMapper.convertValue(user, Map.class);
        var token = jwtService.generateToken(claims, user);
        tokenControl.revokeAllUserTokens(user);
        tokenControl.saveUserToken(user, token);
        return new AuthResponseDto(token);
    }
}
