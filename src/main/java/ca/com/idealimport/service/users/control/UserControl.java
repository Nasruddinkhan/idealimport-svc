package ca.com.idealimport.service.users.control;

import ca.com.idealimport.service.role.control.RoleControl;
import ca.com.idealimport.common.mapper.UserMapper;
import ca.com.idealimport.service.users.boundry.repository.UserRepository;
import ca.com.idealimport.service.users.entity.User;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Observed(name = "user")
@Transactional
public class UserControl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleControl roleControl;

    public UserRegistrationResponse createUser(UserDto userDto) {

        log.debug("UserControl.createPermission start {}", userDto);
        final var roles = roleControl.findRoleByIds(userDto.role());
        var responseDto = Optional.ofNullable(userDto)
                .map(e -> UserMapper.convertDtoToEntity(passwordEncoder, e, roles))
                .map(userRepository::save)
                .map(UserMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("Invalid argument exception"));
        log.debug("UserControl.createPermission end {}", responseDto);
        return responseDto;
    }

    public User findUserByEmailOrId(String userId) {
        log.debug("UserControl.findUserByEmailOrId start {}", userId);
        var user = userRepository.findByUserName(userId)
                .orElseThrow(() -> new RuntimeException("record not present"));
        log.debug("UserControl.findUserByEmailOrId end {}", user);
    return user;
    }
}
