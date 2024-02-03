package ca.com.idealimport.service.purchaseorder.repository;

import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderIdKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, PurchaseOrderIdKey> {
    Page<PurchaseOrder> findAll(Specification<PurchaseOrder> specification, Pageable pageable);

}
