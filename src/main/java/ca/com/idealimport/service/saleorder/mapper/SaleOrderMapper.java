package ca.com.idealimport.service.saleorder.mapper;

import ca.com.idealimport.common.constants.ErrorConstants;
import ca.com.idealimport.common.constants.MessageConstants;
import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.customer.entity.dto.CustomerDto;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.saleorder.entity.Amount;
import ca.com.idealimport.service.saleorder.entity.OrderItem;
import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import ca.com.idealimport.service.saleorder.entity.SaleOrderInfo;
import ca.com.idealimport.service.saleorder.entity.SaleOrderItem;
import ca.com.idealimport.service.saleorder.entity.dto.AmountDto;
import ca.com.idealimport.service.saleorder.entity.dto.OrderItemDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderCreationResponse;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderInfoDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderItemDto;
import ca.com.idealimport.service.saleorder.entity.dto.SaleOrderResponse;
import ca.com.idealimport.service.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

import static ca.com.idealimport.common.util.CommonUtils.safeValue;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SaleOrderMapper {

    default OrderItem validateAndGetOrderItem(ProductItem productItem, SaleOrderItemDto item, String itemCode, User user) {
        final var orderItem = item.orderItem();
        final int totalQty = safeValue(orderItem.xs()) + safeValue(orderItem.s()) + safeValue(orderItem.m()) + safeValue(orderItem.l())
                + safeValue(orderItem.xl()) + safeValue(orderItem.xxl()) + safeValue(orderItem.xxxl())
                + safeValue(orderItem.mixed());
        final BigDecimal totalQuantityBD = BigDecimal.valueOf(totalQty);

        return OrderItem.builder()
                .orderItemId(CommonUtils.getUUID(orderItem.orderItemId()))
                .xs(getSizeItem(productItem.getXs(), orderItem.xs(), orderItem.color(), itemCode))
                .s(getSizeItem(productItem.getS(), orderItem.s(), orderItem.color(), itemCode))
                .xl(getSizeItem(productItem.getXl(), orderItem.xl(), orderItem.color(), itemCode))
                .m(getSizeItem(productItem.getM(), orderItem.m(), orderItem.color(), itemCode))
                .l(getSizeItem(productItem.getL(), orderItem.l(), orderItem.color(), itemCode))
                .xxl(getSizeItem(productItem.getXxl(), orderItem.xxl(), orderItem.color(), itemCode))
                .xxxl(getSizeItem(productItem.getXxxl(), orderItem.xxxl(), orderItem.color(), itemCode))
                .mixed(getSizeItem(productItem.getMixed(), orderItem.mixed(), orderItem.color(), itemCode))
                .qty(totalQty)
                .subTotal(totalQuantityBD.multiply(orderItem.unitPrice()))
                .unitPrice(orderItem.unitPrice())
                .productItemId(productItem)
                .color(orderItem.color())
                .isActive(true)
                .user(user)
                .build();
    }


    private Integer getSizeItem(Integer productSize, Integer saleOrderSize, String color, String itemCode) {
        if (saleOrderSize > productSize)
            throw new IdealException(IdealResponseErrorCode.NOT_FOUND,
                    String.format(ErrorConstants.NO_PRODUCT_PRESENT_INVENTORY, itemCode, color, saleOrderSize));
        return saleOrderSize;
    }

    @Mapping(target = "saleOrderInfoId", expression = "java(getCommonId(saleOrderInfoDto.saleOrderInfoId()))")
    SaleOrderInfo getSaleOrderInfo(SaleOrderInfoDto saleOrderInfoDto);

    default String getCommonId(String value) {
        return CommonUtils.getUUID(value);
    }

    @Mapping(target = "amountId", expression = "java(getCommonId(amount.amountId()))")
    @Mapping(target = "discount", expression = "java(setScaleToTwoDecimals(amount.discount()))")
    @Mapping(target = "totalAmount", expression = "java(setScaleToTwoDecimals(amount.totalAmount()))")
    @Mapping(target = "paid", expression = "java(setScaleToTwoDecimals(amount.paid()))")
    @Mapping(target = "subTotal", expression = "java(setScaleToTwoDecimals(amount.subTotal()))")
    Amount getSaleOrderAmount(AmountDto amount);

    AmountDto getSaleOrderAmount(Amount amount);

    default BigDecimal setScaleToTwoDecimals(BigDecimal value) {
        return value != null ? value.setScale(2, BigDecimal.ROUND_HALF_UP) : null;
    }

    default SaleOrderCreationResponse createSaleOrderResponse(SaleOrder saleOrder, AmountDto amountDto) {
        String name = saleOrder.getCustomer().getCustomerName();
        String trackingId = saleOrder.getTrackingId();
        long qty = saleOrder.getItems().stream().map(SaleOrderItem::getOrderItem)
                .map(OrderItem::getQty).reduce(0, Integer::sum);
        return SaleOrderCreationResponse.builder()
                .msg(String.format(MessageConstants.SALE_ORDER_RES_MSG, name,
                        saleOrder.getTrackingId()))
                .status(saleOrder.getOrderStatus())
                .name(name)
                .trackingId(trackingId)
                .amountDto(amountDto)
                .qty(qty)
                .build();
    }

    default SaleOrderResponse convertSaleOrderToDtoResponse(SaleOrder saleOrder) {
        return SaleOrderResponse.builder()
                .orderInfo(getSaleOrderInfo(saleOrder.getSaleOrderInfo()))
                .amount(getSaleOrderAmount(saleOrder.getAmounts()))
                .customer(getCustomer(saleOrder.getCustomer()))
                .items(getSaleOrderItem(saleOrder.getItems()))
                .trackingId(saleOrder.getTrackingId())
                .saleOrderId(saleOrder.getSaleOrderId())
                .orderStatus(saleOrder.getOrderStatus())
                .build();
    }

    CustomerDto getCustomer(Customer customer);


    default List<SaleOrderItemDto> getSaleOrderItem(List<SaleOrderItem> items) {
        return items.stream().map(e -> SaleOrderItemDto.builder()
                .party(DropDownDto.builder().key(e.getParty().getPartyId().toString()).value(e.getParty().getFullName()).build())
                .itemCode(e.getItemCode())
                .saleOrderItemId(e.getSaleOrderItemId())
                .orderItem(getSaleOrderItem(e.getOrderItem()))
                .build()).toList();
    }

    @Mapping(target = "productItemId", source = "item.productItemId.productItemId")
    OrderItemDto getSaleOrderItem(OrderItem item);

    SaleOrderInfoDto getSaleOrderInfo(SaleOrderInfo saleOrderInfo);
}
