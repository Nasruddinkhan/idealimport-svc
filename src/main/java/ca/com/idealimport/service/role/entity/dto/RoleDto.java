package ca.com.idealimport.service.role.entity.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public record RoleDto(Long roleId, String name, Set<Long> permissions, String module, int sort) {
    public RoleDto addPermissions(Set<Long> newPermissions) {
        Set<Long> updatedPermissions = new HashSet<>(Optional.ofNullable(this.permissions)
                .orElse(newPermissions)
        );
       // this.permissions = newPermissions;
        //updatedPermissions.addAll(newPermissions);
        return new RoleDto(this.roleId, this.name, updatedPermissions, this.module, this.sort);
    }
}
