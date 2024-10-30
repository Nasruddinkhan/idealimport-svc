package ca.com.idealimport.service.saleorder.repository;

import ca.com.idealimport.service.saleorder.entity.Amount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SOrderAmountRepository extends JpaRepository<Amount, String> {
}
