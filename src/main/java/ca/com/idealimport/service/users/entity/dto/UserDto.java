package ca.com.idealimport.service.users.entity.dto;

import ca.com.idealimport.common.dto.DropDownDto;
import lombok.Builder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Builder
public record UserDto(Long userId, String lastName, String firstName, String email, Set<Long> role, String mobileNo,
                      String userName, List<DropDownDto> additionalPermission) {
    public UserDto addRoles(Set<Long> newRoles) {
        Set<Long> updatedRoles = new HashSet<>(Optional.ofNullable(this.role).orElse(newRoles));
        return new UserDto(this.userId, this.lastName, this.firstName, this.email, updatedRoles, this.mobileNo, this.userName, this.additionalPermission);
    }
}
