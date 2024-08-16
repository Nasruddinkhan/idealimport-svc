package ca.com.idealimport.service.saleorder.repository;

import ca.com.idealimport.service.saleorder.entity.SaleOrderAmountAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleOrderAmountAuditRepository extends JpaRepository<SaleOrderAmountAudit, String> {
}
