package ca.com.idealimport.service.auth.control;

import ca.com.idealimport.config.jwt.JwtService;
import ca.com.idealimport.service.auth.entity.dto.AuthRequestDto;
import ca.com.idealimport.service.auth.entity.dto.AuthResponseDto;
import ca.com.idealimport.service.token.control.TokenControl;
import ca.com.idealimport.service.users.control.UserControl;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public UserRegistrationResponse registerUser(UserDto userDto, String password) {
        log.info("AuthControl.registerUser start {}", userDto);
        var userRegistrationResponse = userControl.createUser(userDto, password);
        log.info("AuthControl.registerUser end {}", userRegistrationResponse);
        return userRegistrationResponse;
    }

    public AuthResponseDto authRequest(AuthRequestDto authRequestDto) {
        var authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.userName(), authRequestDto.password()));
        return getToken(authenticate.getName());
    }

    public AuthResponseDto authRequest(String userName) {
        return getToken(userName);
    }

    public AuthResponseDto getToken(String  name) {
        var user = userControl.findUserByEmailOrId(name);
        var claims = objectMapper.convertValue(user, Map.class);
        var token = jwtService.generateToken(claims, user);
        tokenControl.revokeAllUserTokens(user);
        tokenControl.saveUserToken(user, token);
        return new AuthResponseDto(token);
    }
}
