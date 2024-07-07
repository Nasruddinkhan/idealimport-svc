package ca.com.idealimport.service.saleorder.service.impl;

import ca.com.idealimport.common.Constants;
import ca.com.idealimport.common.constants.MessageConstants;
import ca.com.idealimport.common.specifications.SpecificationUtils;
import ca.com.idealimport.common.specifications.Specifications;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.common.util.PageUtils;
import ca.com.idealimport.common.util.SecurityUtils;
import ca.com.idealimport.common.util.TracingNumberGenerator;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.customer.control.CustomerControl;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.party.control.PartyControl;
import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.productitem.control.ProductItemControl;
import ca.com.idealimport.service.saleorder.entity.Amount;
import ca.com.idealimport.service.saleorder.entity.OrderItem;
import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import ca.com.idealimport.service.saleorder.entity.SaleOrderInfo;
import ca.com.idealimport.service.saleorder.entity.SaleOrderItem;
import ca.com.idealimport.service.saleorder.entity.SaleOrderStatus;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderCreationResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderItemDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderRequestDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderSearch;
import ca.com.idealimport.service.saleorder.mapper.SaleOrderMapper;
import ca.com.idealimport.service.saleorder.repository.SaleOrderRepository;
import ca.com.idealimport.service.saleorder.service.SaleOrderService;
import ca.com.idealimport.service.saleorder.service.SaleOrderStatusService;
import ca.com.idealimport.service.tax.service.TaxService;
import ca.com.idealimport.service.users.control.UserControl;
import ca.com.idealimport.service.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleOrderServiceImpl implements SaleOrderService {

    private final SaleOrderMapper saleOrderMapper;
    private final UserControl userControl;
    private final CustomerControl customerControl;
    private final PartyControl partyControl;
    private final ProductItemControl productItemControl;
    private final TaxService taxService;
    private final SaleOrderStatusService saleOrderStatusService;
    private final TracingNumberGenerator tracingNumberGenerator;
    private final SaleOrderRepository saleOrderRepository;
    private final PageUtils pageUtils;
    private final MessageSource messageSource;

    @Override
    public SaleOrderCreationResponse createSaleOrder(SaleOrderRequestDto saleOrderRequest) {
        final var saleOrderId = CommonUtils.getUUID(saleOrderRequest.saleOrderId());
        final var customer = customerControl.findCustomer(Long.valueOf(saleOrderRequest.customer().key()));
        final var user = userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId());
        final List<SaleOrderItem> items = validateAndGetSaleOrderItem(saleOrderRequest.items(), user);
        final var saleOrderInfo = saleOrderMapper.getSaleOrderInfo(saleOrderRequest.saleOrderInfo());
        final var amount = saleOrderMapper.getSaleOrderAmount(saleOrderRequest.amount());
        amount.setIsActive(true);
        amount.setTax(taxService.findTax(saleOrderRequest.amount().tax().getTaxId()));
        final var status = saleOrderStatusService.findStatus(saleOrderRequest.orderStatus().key());
        final SaleOrder order = getSaleOrder(saleOrderId, List.of(amount), customer, items, status, saleOrderInfo, user);
        SaleOrder saleOrder = saleOrderRepository.save(order);
        return saleOrderMapper.createSaleOrderResponse(saleOrder, saleOrderRequest.amount());
    }

    @Override
    public Page<SaleOrderResponse> findAllSaleOrder(int page, int size, SaleOrderSearch saleOrderSearch) {
        final var productPage = pageUtils.getPageableOrder(page, size, Sort.Direction.DESC.name(), Constants.CREATED_DATE);
        Specification<SaleOrder> specification = buildWhereConditions(saleOrderSearch, Boolean.TRUE);
        return saleOrderRepository.findAll(specification, productPage)
                .map(saleOrderMapper::convertSaleOrderToDtoResponse);
    }

    @Override
    public SaleOrderResponse findSaleOrderByTrackingId(String trackingId) {
        SaleOrder saleOrder = findByTrackingId(trackingId);
        return saleOrderMapper.convertSaleOrderToDtoResponse(saleOrder);
    }

    public SaleOrder findByTrackingId(String trackingId) {
        return saleOrderRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND,
                        messageSource.getMessage(MessageConstants.TRACKING_NOT_FOUND,
                                new Object[] { trackingId },
                                LocaleContextHolder.getLocale())));
    }

    private Specification<SaleOrder> buildWhereConditions(SaleOrderSearch saleOrderSearch,
                                                          Boolean isActiveOnly) {
        List<Specification<SaleOrder>> specificationsList = new ArrayList<>();
        specificationsList.add(Specifications.fieldProperty(Constants.ACTIVE, isActiveOnly));
        specificationsList.add(Specifications.fieldProperty(Constants.CREATED_BY, SecurityUtils.getLoggedInUserId()));
        return SpecificationUtils.and(specificationsList);
    }

    private SaleOrder getSaleOrder(String saleOrderId, List<Amount> amounts, Customer customer, List<SaleOrderItem> items,
                                   SaleOrderStatus status, SaleOrderInfo saleOrderInfo, User user) {
        return SaleOrder.builder()
                .saleOrderId(saleOrderId)
                .amounts(amounts)
                .customer(customer)
                .items(items)
                .orderStatus(status)
                .user(user)
                .isActive(true)
                .trackingId(tracingNumberGenerator.getSaleOrderTrackingNumber())
                .saleOrderInfo(saleOrderInfo)
                .build();
    }


    private List<SaleOrderItem> validateAndGetSaleOrderItem(List<SaleOrderItemDto> items, User user) {
        return items.stream().map(e -> {
            ProductItem productItem = productItemControl.findProductItemById(e.orderItem().productItemId());
            OrderItem orderItem = saleOrderMapper.validateAndGetOrderItem(productItem, e, e.itemCode(), user);
            Party party = partyControl.findParty(Long.valueOf(e.party().key()));
            return SaleOrderItem.builder()
                    .party(party)
                    .orderItem(orderItem)
                    .saleOrderItemId(CommonUtils.getUUID(e.saleOrderItemId()))
                    .itemCode(e.itemCode())
                    .build();
        }).toList();
    }
}
