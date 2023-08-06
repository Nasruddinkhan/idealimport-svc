package ca.com.idealimport.service.productitem.boundry;

import ca.com.idealimport.service.product.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,String > {
}
