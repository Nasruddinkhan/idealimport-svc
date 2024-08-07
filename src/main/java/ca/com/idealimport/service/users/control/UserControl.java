package ca.com.idealimport.service.users.control;

import ca.com.idealimport.common.constants.MessageConstants;
import ca.com.idealimport.common.mapper.UserMapper;
import ca.com.idealimport.common.pagination.CommonPageable;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.role.control.RoleControl;
import ca.com.idealimport.service.users.boundry.repository.UserRepository;
import ca.com.idealimport.service.users.entity.User;
import ca.com.idealimport.service.users.entity.dto.ChangePasswordRequest;
import ca.com.idealimport.service.users.entity.dto.ChangePasswordResponse;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import ca.com.idealimport.service.users.entity.dto.UserResponseDto;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final MessageSource messageSource;

    public UserRegistrationResponse createUser(UserDto userDto, String password) {

        log.debug("UserControl.createPermission start {}", userDto);
        final var roles = roleControl.findRoleByIds(userDto.role());
        var responseDto = Optional.ofNullable(userDto)
                .map(e -> UserMapper.convertDtoToEntity(e, roles))
                .map(e -> updatePassword(e, password))
                .map(userRepository::save)
                .map(UserMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("Invalid argument exception"));
        log.debug("UserControl.createPermission end {}", responseDto);
        return responseDto;
    }

    private User updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    public User findUserByEmailOrId(String userId) {
        log.debug("UserControl.findUserByEmailOrId start {}", userId);
        var user = userRepository.findByUserName(userId)
                .orElseThrow(() -> new RuntimeException("record not present"));
        log.debug("UserControl.findUserByEmailOrId end {}", user);
        return user;
    }

    public Optional<User> findUserByEmail(String email) {
        log.debug("UserControl.findUserByEmail start {}", email);
        var user = userRepository.findUserByEmail(email);
        log.debug("UserControl.findUserByEmail end {}", user);
        return user;
    }

    public Page<UserResponseDto> findAllUser(int page, int size) {
        log.debug("UserControl.findAllUser page {}, size {}", page, size);
        final var pageable = new CommonPageable(page, size, Sort.by("userId").descending());
        final var users = userRepository
                .findAllAndIsActiveTrueAndUserNameNot(pageable, SecurityUtils.getLoggedInUserId()).map(UserMapper::convertEntityToUserDto);
        log.debug("UserControl.findAllUser users {}", users);
        return users;
    }

    public List<UserResponseDto> findAllUser() {
        log.debug("UserControl.findAllUser start");
        final var users = userRepository.findAll().stream().map(UserMapper::convertEntityToUserDto).toList();
        log.debug("UserControl.findAllUser end");
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

    public ChangePasswordResponse changePassword(ChangePasswordRequest passwordRequest) {
        if (!passwordRequest.confirmPassword().equals(passwordRequest.newPassword()))
            throw new IdealException(IdealResponseErrorCode.INVALID_CHANGE_PASSWORD);
        final User user = updatePassword(findUserByEmailOrId(SecurityUtils.getLoggedInUserId()), passwordRequest.newPassword());
        final User savedUser = userRepository.save(user);
        String msg = messageSource.getMessage(MessageConstants.CHANGE_PASSWORD_MSG, new Object[]{String.format("%s %s", savedUser.getFirstName(), savedUser.getLastName())},
                LocaleContextHolder.getLocale());
        return ChangePasswordResponse.builder().msg(msg).build();
    }
}
