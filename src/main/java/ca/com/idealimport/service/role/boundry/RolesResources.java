package ca.com.idealimport.service.role.boundry;

import ca.com.idealimport.service.role.control.RoleControl;
import ca.com.idealimport.service.role.entity.dto.RoleDto;
import ca.com.idealimport.service.role.entity.dto.RoleResponseDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
public record RolesResources(RoleControl roleControl) {

    /**
     * @param roleDto
     * @return
     */
    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleDto roleDto) {
        log.info("RolesResources.createRole start");
        final var role = roleControl.createRole(roleDto);
        log.info("RolesResources.createRole end");
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    /**
     * @param roleName
     * @return
     */
    @GetMapping("/{name}")
    public ResponseEntity<RoleResponseDto> findRoleByName(@PathVariable("name") String roleName) {
        log.info("RolesResources.createRole start");
        final var role = roleControl.findRoleByName(roleName);
        log.info("RolesResources.createRole end");
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    /**
     * @return
     */
    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> findAllRoles() {
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
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable("name") String name, @RequestBody RoleDto roleDto) {
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
    public ResponseEntity<RoleResponseDto> activeAndInActiveRole(@RequestParam("name") String name,
                                                         @RequestParam("status") Boolean status) {
        log.info("RolesResources.activeAndInActiveRole start");
        final var roleDto = roleControl.activeAndInActiveRole(name, status);
        log.info("RolesResources.activeAndInActiveRole start");
        return ResponseEntity.ok(roleDto);
    }
}
