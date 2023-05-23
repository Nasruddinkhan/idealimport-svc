package ca.com.idealimport.service.users.entity.dto;

import java.util.Set;

public record UserDto(Long userId, String lastName, String firstName,String email, Set<Long> role, String mobileNo) {
}
