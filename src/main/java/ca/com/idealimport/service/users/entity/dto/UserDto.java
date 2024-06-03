package ca.com.idealimport.service.users.entity.dto;

import java.util.HashSet;
import java.util.Set;

public record UserDto(Long userId, String lastName, String firstName, String email, Set<Long> role, String mobileNo,
                      String userName) {
    public UserDto addRoles(Set<Long> newRoles) {
        Set<Long> updatedRoles = new HashSet<>(this.role);
        updatedRoles.addAll(newRoles);
        return new UserDto(this.userId, this.lastName, this.firstName, this.email, updatedRoles, this.mobileNo, this.userName);
    }
}
