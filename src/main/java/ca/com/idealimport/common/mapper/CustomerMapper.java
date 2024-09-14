package ca.com.idealimport.common.mapper;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.customer.entity.dto.CustomerDto;
import ca.com.idealimport.service.saleorder.entity.Amount;
import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import ca.com.idealimport.service.saleorder.repository.SaleOrderRepository;
import ca.com.idealimport.service.saleorder.service.SaleOrderService;
import ca.com.idealimport.service.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerMapper {

    private final SaleOrderRepository saleOrderRepository;
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
                .parties(customer.parties())
                .build();
    }

    public CustomerDto convertCustomerToCustomerDto(Customer customer) {
        final BigDecimal balance = saleOrderRepository.findByCustomer(customer).stream().map(SaleOrder::getAmounts).map(Amount::getBalance).reduce(BigDecimal::add)
                .orElse(new BigDecimal("0.0"));
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .companyName(customer.getCompanyName())
                .faxNo(customer.getFaxNo())
                .phoneNo(customer.getPhoneNo())
                .address(customer.getAddress())
                .balance(balance)
                .email(customer.getEmail())
                .remarks(customer.getRemarks())
                .parties(customer.getParties().stream().map(e -> new DropDownDto(e.key(), e.value())).toList())
                .build();
    }

    public Customer setActiveDeActiveFlag(Customer customer, Boolean isActiveFlag) {
        customer.setIsActive(isActiveFlag);
        return customer;
    }
}
