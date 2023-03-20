package ca.com.idealimport.service.permissions.control.repository;

import ca.com.idealimport.service.permissions.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByNameAndIsActiveTrue(String name);

    Optional<Permission> findByName(String name);

    Set<Permission> findByNameIn(Set<String> name);
}
