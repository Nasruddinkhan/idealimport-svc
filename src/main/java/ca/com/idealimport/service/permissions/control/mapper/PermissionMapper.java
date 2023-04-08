package ca.com.idealimport.service.permissions.control.mapper;

import ca.com.idealimport.service.permissions.entity.Permission;
import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;

public class PermissionMapper {
    private PermissionMapper(){}
    public static Permission convertDtoToEntity(final PermissionDto permissionDto) {
        return Permission.builder().name(permissionDto.name()).isActive(true).module(permissionDto.module()).build();
    }

    public static PermissionDto convertEntityToDto(final Permission permission) {
        return new PermissionDto(permission.getName(), permission.getModule(), permission.getSort());
    }

    public static Permission convertEntityToDto(Permission permission, PermissionDto permissionDto) {
        permission.setName(permissionDto.name());
        permission.setModule(permissionDto.module());
        return permission;
    }

    public static Permission setIsActivePermission(Permission permission) {
        permission.setIsActive(true);
        return permission;
    }

    public static Permission setIsInActivePermission(Permission permission) {
        permission.setIsActive(false);
        return permission;
    }

}
