package ca.com.idealimport.service.users.control;

import ca.com.idealimport.common.mapper.UserMapper;
import ca.com.idealimport.common.pagination.CommonPageable;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.role.control.RoleControl;
import ca.com.idealimport.service.users.boundry.repository.UserRepository;
import ca.com.idealimport.service.users.entity.User;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import ca.com.idealimport.service.users.entity.dto.UserResponseDto;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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
                .map(e -> UserMapper.convertDtoToEntity(e, roles))
                .map(this::updatePassword)
                .map(userRepository::save)
                .map(UserMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("Invalid argument exception"));
        log.debug("UserControl.createPermission end {}", responseDto);
        return responseDto;
    }

    private User updatePassword(User user) {
        String password = UUID.randomUUID().toString();
        System.out.println("password : ["+password+"]");
        user.setPassword(password);
        return user;
    }

    public User findUserByEmailOrId(String userId) {
        log.debug("UserControl.findUserByEmailOrId start {}", userId);
        var user = userRepository.findByUserName(userId)
                .orElseThrow(() -> new RuntimeException("record not present"));
        log.debug("UserControl.findUserByEmailOrId end {}", user);
        return user;
    }

    public Page<UserResponseDto> findAllUser(int page, int size) {
        log.debug("UserControl.findAllUser page {}, size {}", page, size);
        final var pageable = new CommonPageable(page, size, Sort.by("userId").descending());
        final var users = userRepository
                .findAllAndIsActiveTrue(pageable).map(UserMapper::convertEntityToUserDto);
        log.debug("UserControl.findAllUser users {}", users);
        return users;
    }

    public UserResponseDto updateUser(UserDto userDto) {

        log.debug("UserControl.updateUser start {}", userDto);
        final var roles = roleControl.findRoleByIds(userDto.role());
        final var user = userRepository.findById(userDto.userId())
                .map(e -> UserMapper.convertDtoToEntity(e, roles, userDto))
                .map(userRepository::save)
                .map(UserMapper::convertEntityToUserDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, "User not found for this id"));
        log.debug("UserControl.updateUser end {}", user);
        return user;
    }

    public UserResponseDto findUserById(String userId) {
        log.debug("UserControl.findUserById start {}", userId);
        final var user = userRepository.findByUserName(userId)
                .map(UserMapper::convertEntityToUserDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, "User not found for this id"));
        log.debug("UserControl.findUserById end {}", user);
        return user;
    }
}
