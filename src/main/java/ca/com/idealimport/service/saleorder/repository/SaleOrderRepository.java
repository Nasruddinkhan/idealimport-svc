package ca.com.idealimport.service.saleorder.repository;

import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, String> {
    Optional<SaleOrder> findByTrackingId(String trackingId);
    Page<SaleOrder> findAll(Specification<SaleOrder> specification, Pageable pageable);


    @Modifying
    @Query("Update SaleOrder s set s.orderStatus = :STATUS where s.saleOrderId = :ID" )
    int updateStatus(@Param("STATUS") SaleOrderStatusEnum saleOrderStatusEnum,@Param("ID") String saleOrderId);

    List<SaleOrder> findByCustomer(Customer customer);
}
