package ca.com.idealimport.common.mapper;

import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.customer.entity.dto.CustomerDto;
import ca.com.idealimport.service.users.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerMapper {
    public Customer convertCustomerDtoToCustomer(CustomerDto customer, User user) {
        return Customer.builder()
                .customerId(customer.customerId())
                .customerName(customer.customerName())
                .companyName(customer.companyName())
                .faxNo(customer.faxNo())
                .phoneNo(customer.phoneNo())
                .address(customer.address())
                .balance(customer.balance())
                .email(customer.email())
                .remarks(customer.remarks())
                .isActive(Boolean.TRUE)
                .user(user)
                .build();
    }

    public CustomerDto convertCustomerToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .companyName(customer.getCompanyName())
                .faxNo(customer.getFaxNo())
                .phoneNo(customer.getPhoneNo())
                .address(customer.getAddress())
                .balance(customer.getBalance())
                .email(customer.getEmail())
                .remarks(customer.getRemarks())
                .build();
    }

    public Customer setActiveDeActiveFlag(Customer customer, Boolean isActiveFlag) {
        customer.setIsActive(isActiveFlag);
        return customer;
    }
}
