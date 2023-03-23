package ca.com.idealimport.service.users.entity.dto;

import java.util.Set;

public record UserDto(String lastName, String firstName,String email, Set<String> role) {
}
