package ca.com.idealimport.service.customer.boundry.repository;

import ca.com.idealimport.common.pagination.CommonPageable;
import ca.com.idealimport.service.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByIsActiveTrue(CommonPageable pageable);

    Optional<Customer> findByCustomerIdAndIsActiveTrue(Long customerId);

    Customer findByEmail(String email);
}
