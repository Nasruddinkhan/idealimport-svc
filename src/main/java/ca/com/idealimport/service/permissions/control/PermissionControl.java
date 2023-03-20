package ca.com.idealimport.service.permissions.control;

import ca.com.idealimport.service.permissions.control.mapper.PermissionMapper;
import ca.com.idealimport.service.permissions.control.repository.PermissionRepository;
import ca.com.idealimport.service.permissions.entity.Permission;
import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;
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
@Slf4j
@RequiredArgsConstructor
@Observed(name = "permissionService")
@Transactional
public class PermissionControl {

    private final PermissionRepository permissionRepository;

    public PermissionDto createPermission(final PermissionDto permissionDto) {
        log.debug("PermissionControl.createPermission start {}", permissionDto);
        final var dto = permissionRepository.findByName(permissionDto.name())
                .map(PermissionMapper::setIsActivePermission).map(permissionRepository::save)
                .map(PermissionMapper::convertEntityToDto).orElseGet(() -> Optional.of(permissionDto)
                        .map(PermissionMapper::convertDtoToEntity)
                        .map(permissionRepository::save)
                        .map(PermissionMapper::convertEntityToDto)
                        .orElse(null));
        log.debug("PermissionControl.createPermission end dto {}", dto);
        return dto;
    }

    public PermissionDto findByName(final String name) {
        log.debug("PermissionControl.createPermission start {}", name);
        final var dto = permissionRepository.findByNameAndIsActiveTrue(name)
                .map(PermissionMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("passing invalid parameter " + name));
        log.debug("PermissionControl.createPermission end {}", name);
        return dto;
    }

    public List<PermissionDto> findAllPermission() {
        log.debug("PermissionControl.createPermission start ");
        final var dto = permissionRepository.findAll().stream()
                .filter(Permission::getIsActive)
                .map(PermissionMapper::convertEntityToDto)
                .toList();
        log.debug("PermissionControl.createPermission end {}", dto);
        return dto;
    }

    public Set<Permission> findAllPermission(Set<String> name) {
        log.debug("PermissionControl.findAllPermission start ");
        Set<Permission> permission = permissionRepository.findByNameIn(name);
        if (permission.isEmpty()) throw new RuntimeException("passing invalid permission");
        log.debug("PermissionControl.findAllPermission end {}", permission);
        return permission;
    }

    public PermissionDto updatePermission(final String name, final PermissionDto permissionDto) {
        log.debug("PermissionControl.createPermission start ");
        final var dto = permissionRepository.findByNameAndIsActiveTrue(name)
                .map(e -> PermissionMapper.convertEntityToDto(e, permissionDto))
                .map(permissionRepository::save)
                .map(PermissionMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("passing invalid parameter " + name));
        log.debug("PermissionControl.createPermission end ");
        return dto;
    }

    public void deleteByName(String name) {
        log.debug("PermissionControl.deleteByName start ");
        final var dto = permissionRepository.findByNameAndIsActiveTrue(name)
                .map(PermissionMapper::setIsInActivePermission)
                .map(permissionRepository::save)
                .map(PermissionMapper::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("passing invalid parameter " + name));
        log.debug("PermissionControl.deleteByName end ", dto);

    }
}
