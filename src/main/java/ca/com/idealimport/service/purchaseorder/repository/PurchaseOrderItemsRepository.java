package ca.com.idealimport.service.purchaseorder.repository;

import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItemIdKey;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemsRepository extends JpaRepository<PurchaseOrderItems, PurchaseOrderItemIdKey> {
}
