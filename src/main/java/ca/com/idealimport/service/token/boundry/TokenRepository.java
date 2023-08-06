package ca.com.idealimport.service.token.boundry;

import ca.com.idealimport.service.token.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query(value = """
      select t from TokenEntity t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<TokenEntity> findAllValidTokenByUser(Long id);

    Optional<TokenEntity> findByToken(String token);
}
