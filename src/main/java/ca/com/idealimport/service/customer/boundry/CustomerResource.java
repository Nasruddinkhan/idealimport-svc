package ca.com.idealimport.service.customer.boundry;

import ca.com.idealimport.service.customer.control.CustomerControl;
import ca.com.idealimport.service.customer.entity.dto.CustomerDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/v1.0")
@Slf4j
@SecurityRequirement(name = "ideal-api")
@OpenAPIDefinition()
public record CustomerResource(CustomerControl customerControl) {

    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        log.info("CustomerResource.addCustomer start customer = {} ", customerDto);
        final var customer = customerControl.addCustomer(customerDto);
        log.info("UserResource.addCustomer end");
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> findAllCustomer(@RequestParam("page") int page,
                                                             @RequestParam("size") int size){
        log.info("UserResource.findAllCustomer start");
        final var allUser = customerControl.findAllCustomer(page, size);
        log.info("UserResource.findAllCustomer end");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }
}
