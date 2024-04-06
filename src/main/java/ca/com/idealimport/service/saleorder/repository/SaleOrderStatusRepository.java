package ca.com.idealimport.service.saleorder.repository;

import ca.com.idealimport.service.saleorder.entity.SaleOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleOrderStatusRepository extends JpaRepository<SaleOrderStatus, String> {
    List<SaleOrderStatus> findAllByIsActiveTrue();
}
