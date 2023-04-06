package ca.com.idealimport.service.permissions.boundry;

import ca.com.idealimport.service.permissions.control.PermissionControl;
import ca.com.idealimport.service.permissions.entity.dto.PermissionDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
public record PermissionResources(PermissionControl permissionControl) {

    @PostMapping
    public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto permissionDto) {
        log.debug("PermissionResources.createPermission start");
        final var permission = permissionControl.createPermission(permissionDto);
        log.debug("PermissionResources.createPermission end");
        return new ResponseEntity<>(permission, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PermissionDto> findByName(@PathVariable("name") String name) {
        log.debug("PermissionResources.findByName start {}", name);
        final var permissionDto = permissionControl.findByName(name);
        log.debug("PermissionResources.findByName end {}", permissionDto);
        return new ResponseEntity<>(permissionDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PermissionDto>> findAllPermission(@RequestParam("page") int page,
                                                                 @RequestParam("size") int size) {
        log.debug("PermissionResources.findByName start");
        final var permissionDtos = permissionControl.findAllPermission(page, size);
        log.debug("PermissionResources.findByName end");
        return new ResponseEntity<>(permissionDtos, HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<PermissionDto> updatePermission(@PathVariable("name") String name, @RequestBody PermissionDto permissionDto) {
        log.debug("PermissionResources.createPermission start");
        final var permission = permissionControl.updatePermission(name, permissionDto);
        log.debug("PermissionResources.createPermission end");
        return new ResponseEntity<>(permission, HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable("name") String name) {
        log.debug("PermissionResources.findByName start");
        permissionControl.deleteByName(name);
        log.debug("PermissionResources.findByName end");
        return ResponseEntity.noContent().build();
    }
}
