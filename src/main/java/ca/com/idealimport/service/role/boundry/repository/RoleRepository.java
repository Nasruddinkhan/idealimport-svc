package ca.com.idealimport.service.role.boundry.repository;


import ca.com.idealimport.service.permissions.entity.Permission;
import ca.com.idealimport.service.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameAndIsActiveTrue(String name);
    Optional<Role> findByName(String name);

    List<Role> findByIsActiveTrue();

    Set<Role>  findByNameInAndIsActiveTrue(Set<String> name);

    Set<Role> findByRoleIdIn(Set<Long> roleId);
}
