package ca.com.idealimport.service.party.boundry;

import ca.com.idealimport.service.party.entity.Party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    Page<Party> findAll( Specification<Party> specification, Pageable partyPage);

    Optional<Party> findByPartyIdAndIsActiveTrue(String partyId);
}
