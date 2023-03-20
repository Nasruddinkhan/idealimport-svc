package ca.com.idealimport.service.role.control.mapper;

import ca.com.idealimport.service.permissions.control.mapper.PermissionMapper;
import ca.com.idealimport.service.permissions.entity.Permission;
import ca.com.idealimport.service.role.entity.Role;
import ca.com.idealimport.service.role.entity.dto.RoleDto;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static Role convertDtoToEntity(RoleDto roleDto, Set<Permission> permission) {
        return Role.builder().isActive(true).name(roleDto.name())
                .permissions(permission)
                .build();
    }

    public static RoleDto convertEntityToDto(Role role) {
        final var permissionDtos = role.getPermissions().stream()
                .map(PermissionMapper::convertEntityToDto).collect(Collectors.toSet());
        return new RoleDto(role.getName(), permissionDtos);
    }

    public static Role setIsActivePermission(Role role) {
        role.setIsActive(true);
        return role;
    }
    public static Role setIsInActivePermission(Role role) {
        role.setIsActive(false);
        return role;
    }
    public static Role setPermissionToRole(Set<Permission> permissions, Role role) {
        role.setPermissions(permissions);
        return role;
    }


    public static Role setStatus(Role role, Boolean status) {
        role.setIsActive(status);
        return role;
    }
}
