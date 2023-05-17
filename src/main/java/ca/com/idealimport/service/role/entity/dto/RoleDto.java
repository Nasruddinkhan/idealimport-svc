package ca.com.idealimport.service.role.entity.dto;

import java.util.Set;

public record RoleDto(Long roleId, String name, Set<Long> permissions, String module, int sort) {
}
