package ca.com.idealimport.service.pricelist.repository;

import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.pricelist.entity.ItemPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemPriceHistoryRepository extends JpaRepository<ItemPriceHistory,Long> {
    Collection<ItemPriceHistory> findAllByCustomer(Customer customer);
}
