package ca.com.idealimport.service.saleorder.repository;

import ca.com.idealimport.service.saleorder.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SOrderItemRepository extends JpaRepository<OrderItem, String> {
}
