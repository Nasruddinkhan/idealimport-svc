package ca.com.idealimport.service.purchaseorder.repository;

import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItemIdKey;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemsRepository extends JpaRepository<PurchaseOrderItems, PurchaseOrderItemIdKey> {
    @Query("""
            SELECT COALESCE(SUM(poi.totalQuantity), 0) AS totalContainerQuantity
            FROM PurchaseOrder po
            JOIN po.purchaseOrderItems poi
            WHERE po.shippingStatus = 'IN_TRANSIT'
            AND poi.itemCode = :itemCode
            AND poi.purchaseOrderItemIdKey.party = :party
            AND po.isActive = 1
            AND poi.isActive = 1
            """)
    Integer getContainerQuantity(@Param("party") Party party, @Param("itemCode") String itemCode);
}
