package ca.com.idealimport.common.mapper;

import ca.com.idealimport.common.util.RandomValueGenerator;
import ca.com.idealimport.service.role.entity.Role;
import ca.com.idealimport.service.users.entity.User;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import ca.com.idealimport.service.users.entity.dto.UserResponseDto;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    private UserMapper() {
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static User convertDtoToEntity(UserDto userDto, Set<Role> roles) {

        return User.builder()
                .userName(
                        Optional.ofNullable(userDto.userName()).orElse(
                                generateUserName(userDto.firstName(), userDto.lastName(), new Random()))
                )
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email().toLowerCase())
                .roles(roles)
                .mobileNo(userDto.mobileNo())
                .additionalPermission(userDto.additionalPermission())
                .isActive(true)
                .build();
    }

    public static User convertDtoToEntity(User user, Set<Role> roles, UserDto userDto) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email().toLowerCase());
        user.setRoles(roles);
        user.setMobileNo(userDto.mobileNo());
        user.setAdditionalPermission(userDto.additionalPermission());
        user.setIsActive(true);
        return user;
    }


    public static String generateUserName(String firstName, String lastName, Random random) {
        var userId = random.ints(10000, 100000)
                .mapToObj(Integer::toString)
                .findFirst().orElse("00000")
                + lastName.substring(0, 2).toUpperCase()
                + firstName.substring(0, 2).toUpperCase();
        var twoStr = RandomValueGenerator.generateRandomString(1, CHARACTERS, random);
        return twoStr + userId;
    }

    public static UserResponseDto convertEntityToUserDto(User user) {

        return UserResponseDto.builder()
                .roles(user.getRoles().stream().map(RoleMapper::convertEntityToDto).collect(Collectors.toSet()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .mobileNo(user.getMobileNo())
                .isActive(user.getIsActive())
                .additionalPermission(user.getAdditionalPermission())
                .build();
    }

    public static UserRegistrationResponse convertEntityToDto(User user) {
        var msg = " has successfully register! your credential will be shared via email in 24 hours";
        return new UserRegistrationResponse(String.format("%s %s", user.getEmail(), msg), user.getUserName());
    }
}
