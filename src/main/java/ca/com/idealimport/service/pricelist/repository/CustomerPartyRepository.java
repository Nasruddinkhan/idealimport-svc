package ca.com.idealimport.service.pricelist.repository;

import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.pricelist.entity.CustomerParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerPartyRepository extends JpaRepository<CustomerParty, Long> {
    Optional<CustomerParty> findByCustomerAndParty(Customer customer, Party party);
}
