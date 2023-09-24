package ca.com.idealimport.service.tax.boundry.repository;

import ca.com.idealimport.service.tax.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
    Optional<Tax> findByTaxIdAndIsActiveTrue(String taxId);

    Optional<List<Tax>> findAllByIsActiveTrue();
}
