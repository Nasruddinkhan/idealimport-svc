package ca.com.idealimport.service.saleorder.repository;

import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, String> {
    Optional<SaleOrder> findByTrackingId(String trackingId);

}
