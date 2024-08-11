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
import ca.com.idealimport.service.saleorder.repository.SOrderAmountRepository;
import ca.com.idealimport.service.saleorder.repository.SOrderItemRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private final SOrderItemRepository sOrderItemRepository;
    private final SOrderAmountRepository sOrderAmountRepository;
    @Override
    public SaleOrderCreationResponse createSaleOrder(SaleOrderRequestDto saleOrderRequest) {
        final var saleOrderId = CommonUtils.getUUID(saleOrderRequest.saleOrderId());
        final var customer = customerControl.findCustomer(Long.valueOf(saleOrderRequest.customer().key()));
        final var user = userControl.findUserByEmailOrId(SecurityUtils.getLoggedInUserId());
        final List<SaleOrderItem> items = validateAndGetSaleOrderItem(saleOrderRequest.items(), user);
        final var saleOrderInfo = saleOrderMapper.getSaleOrderInfo(saleOrderRequest.saleOrderInfo());
        List<Amount> list = null;
        if (Objects.nonNull(saleOrderRequest.amount())) {
            list = new ArrayList<>();
            final var amount = saleOrderMapper.getSaleOrderAmount(saleOrderRequest.amount());
            amount.setIsActive(true);
            if (Objects.nonNull(saleOrderRequest.amount().tax()))
                amount.setTax(taxService.findTax(saleOrderRequest.amount().tax().getTaxId()));
            list.add(amount);
        }
        final var status = saleOrderStatusService.findStatus(saleOrderRequest.orderStatus().key());
        final SaleOrder order = getSaleOrder(saleOrderId, list, customer, items, status, saleOrderInfo, user, saleOrderRequest.trackingId());
        //call update product update in case status approver
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

    @Override
    @Transactional
    public void deleteSaleOrderItem(String orderAmountId, String oderItem) {
        final OrderItem item = sOrderItemRepository.findById(oderItem)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND,
                        messageSource.getMessage(MessageConstants.NO_SO_ORDER_FOUND,
                                null,
                                LocaleContextHolder.getLocale())));
        sOrderItemRepository.deleteById(item.getOrderItemId());
        if (Objects.nonNull(orderAmountId)) {
            final Amount amount = getAmountById(orderAmountId);
            amount.setBalance(amount.getBalance().subtract(item.getSubTotal()));
            amount.setTotalAmount(amount.getTotalAmount().subtract(item.getSubTotal()));
            amount.setSubTotal(amount.getSubTotal().subtract(item.getSubTotal()));
            sOrderAmountRepository.save(amount);
        }
    }

    private Amount getAmountById(final String orderAmountId) {
        return sOrderAmountRepository.findById(orderAmountId).orElseThrow(() -> new IdealException(IdealResponseErrorCode.NOT_FOUND,
                messageSource.getMessage(MessageConstants.NO_SO_ORDER_FOUND,
                        null,
                        LocaleContextHolder.getLocale())));
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
        Optional.ofNullable(saleOrderSearch.saleOrderNo())
                .ifPresent(soNo -> specificationsList.add(Specifications.fieldProperty("saleOrderId", soNo)));
        Optional.ofNullable(saleOrderSearch.status())
                .ifPresent(status -> specificationsList.add(Specifications.fieldProperty(status, "orderStatus",
                        "saleOrderStatusId")));
        Optional.ofNullable(saleOrderSearch.trackingNo())
                .ifPresent(trackingNo -> specificationsList.add(Specifications.fieldProperty("trackingId", trackingNo)));
        if (!SecurityUtils.isAdmin())
            specificationsList.add(Specifications.fieldProperty(Constants.CREATED_BY, SecurityUtils.getLoggedInUserId()));
        return SpecificationUtils.and(specificationsList);
    }

    private SaleOrder getSaleOrder(String saleOrderId, List<Amount> amounts, Customer customer, List<SaleOrderItem> items,
                                   SaleOrderStatus status, SaleOrderInfo saleOrderInfo, User user, String trackingId) {
        return SaleOrder.builder()
                .saleOrderId(saleOrderId)
                .amounts(amounts)
                .customer(customer)
                .items(items)
                .orderStatus(status)
                .user(user)
                .isActive(true)
                .trackingId(Optional.ofNullable(trackingId).orElse(tracingNumberGenerator.getSaleOrderTrackingNumber()))
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
