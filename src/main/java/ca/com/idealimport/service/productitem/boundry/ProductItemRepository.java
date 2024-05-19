package ca.com.idealimport.service.productitem.boundry;

import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,String > {

    Optional<ProductItem> findByDetailsAndProduct(String color, Product product);


}
