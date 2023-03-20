package ca.com.idealimport.service.role.boundry;

import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;
import ca.com.idealimport.service.role.control.RoleControl;
import ca.com.idealimport.service.role.entity.dto.RoleDto;
import ca.com.idealimport.service.role.entity.dto.RoleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role/v1.0")
@Slf4j
public record RolesResources(RoleControl roleControl) {

    /**
     * @param roleDto
     * @return
     */
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        log.info("RolesResources.createRole start");
        final var role = roleControl.createRole(roleDto);
        log.info("RolesResources.createRole end");
        return new ResponseEntity<>(roleDto, HttpStatus.CREATED);
    }

    /**
     * @param roleName
     * @return
     */
    @GetMapping("/{name}")
    public ResponseEntity<RoleDto> findRoleByName(@PathVariable("name") String roleName) {
        log.info("RolesResources.createRole start");
        final var role = roleControl.findRoleByName(roleName);
        log.info("RolesResources.createRole end");
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    /**
     * @return
     */
    @GetMapping
    public ResponseEntity<List<RoleDto>> findAllRoles() {
        log.info("RolesResources.findAllRoles start");
        final var role = roleControl.findAllRoles();
        log.info("RolesResources.findAllRoles end");
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    /**
     * @param name
     * @param roleDto
     * @return
     */
    @PutMapping("/{name}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable("name") String name, @RequestBody RoleDto roleDto) {
        log.info("RolesResources.updateRole start");
        final var role = roleControl.updateRole(name, roleDto);
        log.info("RolesResources.updateRole end");
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteRole(@PathVariable("name") String name) {
        log.info("RolesResources.deleteRole start");
        roleControl.deleteRole(name);
        log.info("RolesResources.deleteRole start");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active-and-deactivate")
    public ResponseEntity<RoleDto> activeAndInActiveRole(@RequestParam("name") String name,
                                                         @RequestParam("status") Boolean status) {
        log.info("RolesResources.activeAndInActiveRole start");
        final var roleDto = roleControl.activeAndInActiveRole(name, status);
        log.info("RolesResources.activeAndInActiveRole start");
        return ResponseEntity.ok(roleDto);
    }
}
