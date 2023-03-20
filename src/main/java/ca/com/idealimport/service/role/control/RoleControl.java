package ca.com.idealimport.service.role.control;

import ca.com.idealimport.service.permissions.control.PermissionControl;
import ca.com.idealimport.service.permissions.entity.Permission;
import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;
import ca.com.idealimport.service.role.control.mapper.RoleMapper;
import ca.com.idealimport.service.role.control.repository.RoleRepository;
import ca.com.idealimport.service.role.entity.Role;
import ca.com.idealimport.service.role.entity.dto.RoleDto;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Observed(name = "roles", contextualName = "roles")
public class RoleControl {

    private final PermissionControl permissionControl;
    private final RoleRepository roleRepository;

    @Observed(name = "createRole", contextualName = "roles")
    public RoleDto createRole(RoleDto roleDto) {
        log.debug("RoleControl.createRole start {}", roleDto);
        final var permissionName = roleDto.permissions().stream().map(PermissionDto::name).collect(Collectors.toUnmodifiableSet());
        final var permission = permissionControl.findAllPermission(permissionName);
        final var roleDos = Optional.of(roleDto)
                .map(r -> RoleMapper.convertDtoToEntity(r, permission))
                .map(roleRepository::save)
                .map(RoleMapper::convertEntityToDto)
                .orElse(null);
        log.debug("RoleControl.createRole end {}", roleDto);
        return roleDos;
    }

    @Observed(name = "findRoleByName", contextualName = "roles")
    public RoleDto findRoleByName(String name) {
        log.debug("RoleControl.findRoleByName start {}", name);
        final var roles = roleRepository.findByNameAndIsActiveTrue(name)
                .map(RoleMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("passing invalid argumentId"));
        log.debug("RoleControl.findRoleByName end {}", roles);
        return roles;
    }

    @Observed(name = "findAllRoles", contextualName = "roles")
    public List<RoleDto> findAllRoles() {
        log.debug("RoleControl.findRoleByName start {}");
        final var roleDos = roleRepository.findByIsActiveTrue()
                .stream().map(RoleMapper::convertEntityToDto)
                .toList();
        log.debug("RoleControl.findRoleByName end {}");
        return roleDos;
    }

    @Observed(name = "updateRole", contextualName = "roles")
    public RoleDto updateRole(String name, RoleDto roleDto) {
        log.debug("RoleControl.updateRole start name {}, roleDto {}", name, roleDto);
        final var role = findRoleByNames(name);
        final var names = getPermissionNames(roleDto);
        final var savedRoleDto = Optional.ofNullable(names)
                .map(this::getPermissionNamesInControl)
                .map(this::findAllPermissions)
                .map(permissions -> RoleMapper.setPermissionToRole(permissions, role))
                .map(roleRepository::save)
                .map(RoleMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("permissions cannot be presents"));
        log.debug("RoleControl.updateRole end role {}", savedRoleDto);
        return savedRoleDto;
    }

    private Role findRoleByNames(String name) {
        return roleRepository.findByNameAndIsActiveTrue(name)
                .orElseThrow(() -> new RuntimeException("passing invalid argumentId"));
    }

    private Set<String> getPermissionNames(RoleDto roles) {
        return roles.permissions()
                .stream()
                .map(PermissionDto::name)
                .collect(Collectors.toSet());
    }

    private Set<String> getPermissionNamesInControl(Set<String> names) {
        return names.stream()
                .map(permissionControl::findByName)
                .map(PermissionDto::name)
                .collect(Collectors.toSet());
    }

    private Set<Permission> findAllPermissions(Set<String> names) {
        return permissionControl.findAllPermission(names)
                .stream()
                .collect(Collectors.toSet());
    }

    public void deleteRole(String name) {
        log.debug("RoleControl.deleteRole end name {}", name);
        final var role = roleRepository.findByNameAndIsActiveTrue(name)
                .map(RoleMapper::setIsInActivePermission)
                .map(roleRepository::save)
                .map(RoleMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("passing invalid role"));
        log.debug("RoleControl.deleteRole end role {}", role);
    }

    public RoleDto activeAndInActiveRole(String name, Boolean status) {
        log.debug("RoleControl.activeAndInActiveRole end name {}, status {}", name, status);

        final var rolesDto = roleRepository.findByName(name)
                .map(e -> RoleMapper.setStatus(e, status))
                .map(roleRepository::save)
                .map(RoleMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("passing invalid arguments"));
        return rolesDto;
    }
}
