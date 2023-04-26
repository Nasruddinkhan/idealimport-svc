package ca.com.idealimport.service.users.boundry.repository;

import ca.com.idealimport.service.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where lower(u.email) = lower(:USER_NAME) or lower(u.userName) = lower(:USER_NAME)")
    Optional<User> findByUserName(@Param("USER_NAME") String username);

}
