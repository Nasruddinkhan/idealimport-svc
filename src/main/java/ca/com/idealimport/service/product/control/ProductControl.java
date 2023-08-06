package ca.com.idealimport.service.product.control;

import ca.com.idealimport.common.Constants;
import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.mapper.ProductItemMapper;
import ca.com.idealimport.common.mapper.ProductMapper;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.boundry.repository.ProductRepository;
import ca.com.idealimport.service.product.entity.ProductKey;
import ca.com.idealimport.service.product.entity.dto.ProductCreationResponse;
import ca.com.idealimport.service.product.entity.dto.ProductDTO;
import ca.com.idealimport.service.product.entity.dto.ProductResponseDto;
import ca.com.idealimport.service.users.control.UserControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductControl {
    private final PartyControl partyControl;
    private final ProductMapper productMapper;
    private final ProductItemMapper productItemMapper;
    private final ProductRepository productRepository;
    private final UserControl userControl;

    private final PageUtils pageUtils;
    @Transactional(readOnly = true)
    public Page<DropDownDto> findAllParty(int page, int size, String fullName, Boolean isActive, String orderBy) {
        log.debug("ProductControl.findAllParty page ={} , size ={}, fullName ={}, isActive={}, orderBy ={}", page, size, fullName, isActive, orderBy);
        final var parties = partyControl.findAllParty(page, size, fullName, isActive, orderBy).map(e -> new DropDownDto(e.partyId().toString(), e.fullName()));
        log.debug("ProductControl.findAllParty  ={}", parties);
        return parties;

    }

    public ProductCreationResponse createProduct(ProductDTO productDTO) {

        return Optional.ofNullable(productDTO)
                .map(dto -> partyControl.findParty(dto.partyId()))
                .map(foundParty -> this.processProduct(foundParty, productDTO))
                .orElseThrow(() -> new RuntimeException("Product cannot be empty"));
    }

    private ProductCreationResponse processProduct(Party foundParty, ProductDTO productDTO) {
        var generatedProductId = CommonUtils.getUUID(productDTO.productId());
        var productKey = productMapper.getProductKey(generatedProductId, foundParty);
        var user = userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId());
        var productEntity = productMapper.getProductDtoToProductEntity(productKey, productDTO, user );
        var productItems = productItemMapper.convertProductItemDtosToEntity(productEntity, productDTO.productItems(), user);
        productEntity.setProductItems(productItems);
        var product = productRepository.save(productEntity);
        return productItemMapper.convertProductItemToProductCreationResponse(product.getProductKey().getProductId());
    }


    public Page<ProductResponseDto> getProducts(int page, int size) {
        log.debug("ProductControl. getProducts");
        final var partyPage = pageUtils.getPageableOrder(page, size,  Sort.Direction.DESC.name(), Constants.PRODUCT_ID);
        var products = productRepository.findAll(partyPage).map(productMapper::convertProductToProductResponse);
        log.debug("ProductControl.getProducts products ={}", products );
        return products;
    }

    public ProductDTO findByProductById(String productId, String name){
        log.debug("ProductControl.findByProductById start productId ={}, name ={}", productId, name);
        final var party = partyControl.findParty(name);
        final var productKey =  productMapper.getProductKey(productId, party);
        final var product = productRepository.findByProductKeyAndIsActiveTrue(productKey)
               .map(productMapper::convertProductEntityToProductDto)
               .orElseThrow(()-> new RuntimeException("no product found for this id"));
        log.debug("ProductControl.findByProductById end product ={}", product);
        return product;

    }
}
