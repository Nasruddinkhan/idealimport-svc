package ca.com.idealimport.service.customer.control;

import ca.com.idealimport.common.enums.RoleEnum;
import ca.com.idealimport.common.mapper.CustomerMapper;
import ca.com.idealimport.common.pagination.CommonPageable;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.customer.boundry.repository.CustomerRepository;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.customer.entity.dto.CustomerDto;
import ca.com.idealimport.service.role.control.RoleControl;
import ca.com.idealimport.service.users.control.UserControl;
import ca.com.idealimport.service.users.entity.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerControl {
    public final CustomerRepository customerRepository;
    public final UserControl userControl;
    public final CustomerMapper customerMapper;
    public final RoleControl roleControl;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        var loggedInUser = SecurityUtils.getLoggedInUserId();
        log.debug("CustomerControl.addCustomer start customerDto = {}, name = {}", customerDto, loggedInUser);
        var custDto = Optional.ofNullable(customerDto)
                .map(cust -> customerMapper.convertCustomerDtoToCustomer(cust, userControl.findUserByEmailOrId(loggedInUser)))
                .map(customerRepository::save)
                .map(customerMapper::convertCustomerToCustomerDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, "customer entity cannot be empty"));
        userControl.findUserByEmail(customerDto.email())
                .ifPresentOrElse(
                        user -> log.info("User present no need to save date in user table"),
                        () -> userControl.createUser(UserDto.builder()
                                .email(customerDto.email())
                                .firstName(customerDto.customerName())
                                .mobileNo(customerDto.phoneNo())
                                .lastName(customerDto.customerName())
                                .role(Set.of(roleControl.findRoleByName(RoleEnum.CUSTOMER.name()).roleId()))
                                .build(), "123456")
                );
        log.debug("CustomerControl.addCustomer end custDto = {}", custDto);
        return custDto;
    }

    public Page<CustomerDto> findAllCustomer(int page, int size) {
        log.debug("CustomerControl.findAllCustomer start page = {}, size = {}", page, size);
        final var pageable = new CommonPageable(page, size, Sort.by("customerId").descending());
        final var customer = customerRepository
                .findByIsActiveTrueAndUser(pageable, userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId()))
                .map(customerMapper::convertCustomerToCustomerDto);
        log.debug("CustomerControl.findAllCustomer end customer = {}", customer);
        return customer;
    }

    public CustomerDto findCustomerByCustomerId(Long customerId) {
        log.debug("CustomerControl.findCustomerByCustomerId start customerId = {}", customerId);
        final var customerDto = customerRepository.findByCustomerIdAndIsActiveTrue(customerId)
                .map(customerMapper::convertCustomerToCustomerDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format("no record present for this %s customer Id", customerId)));
        log.debug("CustomerControl.findAllCustomer end customer = {}", customerDto);
        return customerDto;

    }

    public Customer findCustomer(Long customerId) {
        log.debug("CustomerControl.findCustomer start customerId = {}", customerId);
        final var customer = customerRepository.findByCustomerIdAndIsActiveTrue(customerId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format("no record present for this %s customer Id", customerId)));
        log.debug("CustomerControl.findAllCustomer end customer = {}", customer);
        return customer;

    }

    public Customer findCustomer(String email) {
        return customerRepository.findByEmail(email);
    }
    public CustomerDto deleteCustomerByCustomerId(Long customerId) {
        log.debug("CustomerControl.deleteCustomerByCustomerId start customerId = {}", customerId);
        final var customerDto = customerRepository.findByCustomerIdAndIsActiveTrue(customerId)
                .map(e -> customerMapper.setActiveDeActiveFlag(e, Boolean.FALSE))
                .map(customerRepository::save)
                .map(customerMapper::convertCustomerToCustomerDto)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND, String.format("No record present for this %s customer Id either customer is already blocked", customerId)));
        log.debug("CustomerControl.deleteCustomerByCustomerId end customer = {}", customerDto);
        return customerDto;
    }

}
