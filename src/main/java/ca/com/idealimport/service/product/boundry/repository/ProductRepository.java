package ca.com.idealimport.service.product.boundry.repository;

import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductKey> {

    Optional<Product> findByProductKeyAndIsActiveTrue(ProductKey productKey);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);


    List<Product> findByProductKeyParty(Party party);

    Product findByProductKeyPartyAndItemCode(Party party, String itemCode);

    Optional<Product> findByProductKeyProductIdAndIsActiveTrue(String productId);
}
