package ca.com.idealimport.service.tax.boundry.repository;

import ca.com.idealimport.service.tax.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
}
