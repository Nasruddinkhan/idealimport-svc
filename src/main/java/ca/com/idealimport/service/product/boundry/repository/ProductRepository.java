package ca.com.idealimport.service.product.boundry.repository;

import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductKey> {

    Optional<Product> findByProductKeyAndIsActiveTrue(ProductKey productKey);
}
