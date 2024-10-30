package ca.com.idealimport.service.saleorder.repository;

import ca.com.idealimport.service.saleorder.entity.SaleOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SaleOrderHistoryRepository extends JpaRepository<SaleOrderHistory, String> {
    Collection<SaleOrderHistory> findBySaleOrderId(String saleOrderId);
}
