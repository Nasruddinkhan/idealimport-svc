package ca.com.idealimport.service.product.boundry.repository;

import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductKey> {
}
