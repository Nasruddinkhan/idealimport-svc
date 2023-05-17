package ca.com.idealimport.common.mapper;

import ca.com.idealimport.service.permissions.entity.Permission;
import ca.com.idealimport.service.role.entity.Role;
import ca.com.idealimport.service.role.entity.dto.RoleDto;
import ca.com.idealimport.service.role.entity.dto.RoleResponseDto;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    private RoleMapper(){}
    public static Role convertDtoToEntity(RoleDto roleDto, Set<Permission> permission) {
        return Role.builder().isActive(true)
                .name(roleDto.name())
                .sort(roleDto.sort())
                .module(roleDto.module())
                .roleId(roleDto.roleId())
                .permissions(permission)
                .build();
    }

    public static RoleResponseDto convertEntityToDto(Role role) {
        final var permissionDtos = role.getPermissions().stream()
                .map(PermissionMapper::convertEntityToDto).collect(Collectors.toSet());
        return new RoleResponseDto(role.getRoleId(), role.getName(), permissionDtos, role.getModule(), role.getSort());
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
