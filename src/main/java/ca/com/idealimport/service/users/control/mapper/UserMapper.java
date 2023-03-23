package ca.com.idealimport.service.users.control.mapper;

import ca.com.idealimport.common.util.RandomValueGenerator;
import ca.com.idealimport.service.role.entity.Role;
import ca.com.idealimport.service.users.entity.User;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import ca.com.idealimport.service.users.entity.dto.UserRegistrationResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class UserMapper {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static User convertDtoToEntity(PasswordEncoder passwordEncoder, UserDto userDto, Set<Role> roles) {
        String password = UUID.randomUUID().toString();
        System.out.println(password);
        return User.builder()
                .userName(generateUserName(userDto.firstName(), userDto.lastName()))
                .password(passwordEncoder.encode(password))
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email().toLowerCase())
                .roles(roles)
                .build();
    }

    public static UserRegistrationResponse convertEntityToDto(User user) {
        var msg = " has successfully register! your credential will be shared via email in 24 hours";
        return new UserRegistrationResponse(String.format("%s %s", user.getEmail(), msg ));
    }

//    public static String generateUserName(String firstName, String lastName) {
//
//        var random = new Random();
//        String userId =  random.nextInt(10000)+lastName.substring(0, 2).toUpperCase()+ firstName.substring(0, 2).toUpperCase() ;
//        String twoStr = RandomValueGenerator.generateRandomString(1, CHARACTERS);
//        return twoStr + userId;
//    }

    public static String generateUserName(String firstName, String lastName) {
        var random = new Random();
        var userId = random.ints(10000, 100000)
                .mapToObj(Integer::toString)
                .findFirst().orElse("00000")
                + lastName.substring(0, 2).toUpperCase()
                + firstName.substring(0, 2).toUpperCase();
        var twoStr = RandomValueGenerator.generateRandomString(1, CHARACTERS);
        return twoStr + userId;
    }

}
