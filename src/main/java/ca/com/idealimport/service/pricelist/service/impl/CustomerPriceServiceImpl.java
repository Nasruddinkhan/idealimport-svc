package ca.com.idealimport.service.pricelist.service.impl;

import ca.com.idealimport.common.constants.MessageConstants;
import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.customer.control.CustomerControl;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.pricelist.entity.CustomerParty;
import ca.com.idealimport.service.pricelist.entity.ItemPrice;
import ca.com.idealimport.service.pricelist.entity.dto.CustomerPriceDto;
import ca.com.idealimport.service.pricelist.entity.dto.CustomerPriceResponse;
import ca.com.idealimport.service.pricelist.entity.dto.PriceItemDto;
import ca.com.idealimport.service.pricelist.repository.CustomerPartyRepository;
import ca.com.idealimport.service.pricelist.service.CustomerPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerPriceServiceImpl implements CustomerPriceService {
    private final CustomerControl customerControl;
    private final PartyControl partyControl;
    private final CustomerPartyRepository customerPartyRepository;
    private final PageUtils pageUtils;
    private final MessageSource messageSource;

    /**
     * Later change the mapping with customer price item mapper
     *
     * @param customerPriceDto
     * @return
     */
    @Override
    @Transactional
    public CustomerPriceResponse createCustomerPrice(CustomerPriceDto customerPriceDto) {
        final Customer customer = customerControl.findCustomer(customerPriceDto.customerId());
        final Party party = partyControl.findParty(customerPriceDto.partyId());
        final List<ItemPrice> itemPriceList = customerPriceDto.itemPrices().stream()
                .map(itemDTO -> ItemPrice.builder()
                        .price(itemDTO.amount())
                        .itemPriceId(itemDTO.priceId())
                        .itemId(itemDTO.itemCode())
                        .build()).toList();
        final CustomerParty customerParty = CustomerParty.builder()
                .customerPartyId(customerPriceDto.customerPartyId())
                .itemPrices(itemPriceList)
                .customer(customer)
                .party(party)
                .build();
        itemPriceList.forEach(itemPrice -> itemPrice.setCustomerParty(customerParty));
        CustomerParty customerParty1 = customerPartyRepository.save(customerParty);
        return getCustomerPriceResponse(customerParty1);
    }

    private CustomerPriceResponse getCustomerPriceResponse(CustomerParty customerParty1) {
        return CustomerPriceResponse.builder()
                .customerPartyId(customerParty1.getCustomerPartyId())
                .itemPrices(customerParty1.getItemPrices().stream()
                        .map(e -> PriceItemDto.builder()
                                .priceId(e.getItemPriceId())
                                .amount(e.getPrice())
                                .itemCode(e.getItemId())
                                .build()).toList())
                .customer(DropDownDto.builder()
                        .key(customerParty1.getCustomer().getCustomerId().toString())
                        .value(customerParty1.getCustomer().getCustomerName())
                        .build())
                .party(DropDownDto.builder()
                        .key(customerParty1.getParty().getPartyId().toString())
                        .value(customerParty1.getParty().getFullName())
                        .build())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerPriceResponse> findAllCustomerPrice(int page, int size) {
        final var customerPricePage = pageUtils.getPageableOrder(page, size, Sort.Direction.DESC.name(), "customerPartyId");
        return customerPartyRepository.findAll(customerPricePage).map(this::getCustomerPriceResponse);
    }

    @Override
    public CustomerPriceResponse validatePrice(Long partyId, Long customerId) {
        final Customer customer = customerControl.findCustomer(customerId);
        final Party party = partyControl.findParty(partyId);
        return customerPartyRepository.findByCustomerAndParty(customer, party)
                .map(this::getCustomerPriceResponse)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND,
                        messageSource.getMessage(MessageConstants.PRICE_PRESENT, null,
                                LocaleContextHolder.getLocale())));
    }
}
