package ca.com.idealimport.service.role.entity.dto;

import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;

import java.util.Set;

public record RoleResponseDto (Long roleId, String name, Set<PermissionDto> permissions,String module, int sort){
}
