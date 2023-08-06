package ca.com.idealimport.service.productitem.control;

import ca.com.idealimport.service.productitem.boundry.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductItemControl {
    private final ProductItemRepository productItemRepository;

    public void deleteProductItemById(String productItemId) {
        productItemRepository.deleteById(productItemId);
    }
}
