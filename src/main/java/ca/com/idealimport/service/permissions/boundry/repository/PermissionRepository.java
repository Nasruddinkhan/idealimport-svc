package ca.com.idealimport.service.permissions.boundry.repository;

import ca.com.idealimport.service.permissions.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByNameAndIsActiveTrue(String name);

    Optional<Permission> findByName(String name);

    Set<Permission> findByNameIn(Set<String> name);

    @Query("SELECT u FROM Permission u WHERE u.isActive = true")
    Page<Permission> findAllAndIsActiveTrue(Pageable pageable);

    @Query("SELECT u FROM Permission u WHERE u.isActive = true")
    List<Permission> findAllAndIsActiveTrue();
}
