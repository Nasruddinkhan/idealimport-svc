package ca.com.idealimport.service.role.control;

import ca.com.idealimport.common.mapper.RoleMapper;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.permissions.control.PermissionControl;
import ca.com.idealimport.service.permissions.entity.Permission;
import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;
import ca.com.idealimport.service.role.boundry.repository.RoleRepository;
import ca.com.idealimport.service.role.entity.Role;
import ca.com.idealimport.service.role.entity.dto.RoleDto;
import ca.com.idealimport.service.role.entity.dto.RoleResponseDto;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ca.com.idealimport.config.exception.enums.IdealResponseErrorCode.INVALID_ARGUMENT;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Observed(name = "roles", contextualName = "roles")
public class RoleControl {

    private final PermissionControl permissionControl;
    private final RoleRepository roleRepository;

    @Observed(name = "createRole", contextualName = "roles")
    public RoleResponseDto createRole(RoleDto roleDto) {
        log.debug("RoleControl.createRole start {}", roleDto);
        final var permission = permissionControl.findAllPermission(roleDto.permissions());
        final var roleDos = Optional.of(roleDto)
                .map(r -> RoleMapper.convertDtoToEntity(r, permission))
                .map(roleRepository::save)
                .map(RoleMapper::convertEntityToDto)
                .orElse(null);
        log.debug("RoleControl.createRole end {}", roleDto);
        return roleDos;
    }

    @Observed(name = "findRoleByName", contextualName = "roles")
    public RoleResponseDto findRoleByName(String name) {
        log.debug("RoleControl.findRoleByName start {}", name);
        final var roles = roleRepository.findByNameAndIsActiveTrue(name)
                .map(RoleMapper::convertEntityToDto)
                .orElseThrow(() -> new IdealException(INVALID_ARGUMENT));
        log.debug("RoleControl.findRoleByName end {}", roles);
        return roles;
    }

    @Observed(name = "findAllRoles", contextualName = "roles")
    public List<RoleResponseDto> findAllRoles() {
        log.debug("RoleControl.findAllRoles start {}");
        final var roleDos = roleRepository.findByIsActiveTrue()
                .stream().map(RoleMapper::convertEntityToDto)
                .toList();
        log.debug("RoleControl.findAllRoles end {}");
        return roleDos;
    }

    @Observed(name = "updateRole", contextualName = "roles")
    public RoleResponseDto updateRole(String name, RoleDto roleDto) {
        log.debug("RoleControl.updateRole start name {}, roleDto {}", name, roleDto);
        final var role = findRoleByNames(name);
        final var permissions = getPermissionNames(roleDto);
        final var savedRoleDto = Optional.ofNullable(role)
                .map(r -> RoleMapper.setPermissionToRole(permissions, r))
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


    public Set<Role> findRoleByNames(Set<String> name) {
        var names = roleRepository.findByNameInAndIsActiveTrue(name);
        if (names.isEmpty()) throw new IdealException(INVALID_ARGUMENT);
        return names;
    }

    private Set<Permission> getPermissionNames(RoleDto roles) {
          return permissionControl.findAllPermission(roles.permissions())
                   .stream().collect(Collectors.toSet());
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

    public RoleResponseDto activeAndInActiveRole(String name, Boolean status) {
        log.debug("RoleControl.activeAndInActiveRole start name {}, status {}", name, status);
        final var rolesDto = roleRepository.findByName(name)
                .map(e -> RoleMapper.setStatus(e, status))
                .map(roleRepository::save)
                .map(RoleMapper::convertEntityToDto)
                .orElseThrow(() -> new IdealException(INVALID_ARGUMENT));
        log.debug("RoleControl.activeAndInActiveRole end rolesDto {}", rolesDto);
        return rolesDto;
    }

    public Set<Role> findRoleByIds(Set<Long> role) {
        final var  roles = roleRepository.findByRoleIdIn(role);
        if (roles.isEmpty()) throw new IdealException(IdealResponseErrorCode.NOT_FOUND, "no role found");
        return roles;
    }
}
