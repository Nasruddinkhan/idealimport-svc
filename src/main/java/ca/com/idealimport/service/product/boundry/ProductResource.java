package ca.com.idealimport.service.product.boundry;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.product.control.ProductControl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
@RequiredArgsConstructor
public class ProductResource {

    private final ProductControl productControl;
    @GetMapping("/party")
    public ResponseEntity<Page<DropDownDto>> findAllParty(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size,
                                                          @RequestParam(name = "full-name", required = false) String fullName,
                                                          @RequestParam(name = "order-by", required = false, defaultValue = "DESC") String orderBy,
                                                          @RequestParam(name = "active", required = false, defaultValue = "true") Boolean isActive){

        log.info("ProductResource.findAllParty start page ={}, size={}", page, size);
        final var dropDownDtos = productControl.findAllParty(page, size, fullName, isActive, orderBy);
        log.info("ProductResource.findAllParty end page ={}, size={}", dropDownDtos);
        return ResponseEntity.ok(dropDownDtos);

    }
}
