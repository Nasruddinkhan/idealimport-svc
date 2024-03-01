package ca.com.idealimport.service.purchaseorder.mapper;

import ca.com.idealimport.common.dto.AuditDto;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.purchaseorder.entity.dto.request.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.response.PurchaseOrderResponseDto;
import ca.com.idealimport.service.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseOrderMapper {

    private final PurchaseOrderItemMapper purchaseOrderItemMapper;


    public PurchaseOrder getPurchaseOrderDtoToEntity(final PurchaseOrderDto purchaseOrderDto,
                                                     final String purchaseOrderId,
                                                     final User user) {

        return PurchaseOrder.builder()
                .purchaseOrderId(purchaseOrderId)
                .orderDate(purchaseOrderDto.orderDate())
                .departureDate(purchaseOrderDto.departureDate())
                .containerName(purchaseOrderDto.containerName())
                .lotNumber(purchaseOrderDto.lotNumber())
                .shippingStatus(purchaseOrderDto.status())
                .totalQuantity(purchaseOrderDto.totalQuantity())
                .isActive(Boolean.TRUE)
                .user(user)
                .build();
    }

    public PurchaseOrderResponse convertProductItemToProductCreationResponse(String productId) {
        return PurchaseOrderResponse.builder()
                .msg(String.format("create the purchase successfully with the order id %s", productId))
                .build();
    }


    public PurchaseOrderResponseDto convertPurchaseOrderToDtoResponse(PurchaseOrder purchaseOrder) {
        System.out.println(purchaseOrder.getTotalQuantity());
        return PurchaseOrderResponseDto.builder()
                .auditDto(AuditDto.builder().lastModifiedBy(purchaseOrder.getLastModifiedBy())
                        .createdBy(purchaseOrder.getCreatedBy())
                        .lastModifiedDate(purchaseOrder.getLastModifiedDate())
                        .createdDate(purchaseOrder.getCreatedDate())
                        .build())
                .purchaseOrderId(purchaseOrder.getPurchaseOrderId())
                .orderDate(purchaseOrder.getOrderDate())
                .lotNumber(purchaseOrder.getLotNumber())
                .containerName(purchaseOrder.getContainerName())
                .departureDate(purchaseOrder.getDepartureDate())
                .isActive(purchaseOrder.getIsActive())
                .totalQuantity(purchaseOrder.getTotalQuantity())
                .shippingStatus(purchaseOrder.getShippingStatus())
                .purchaseOrderItems(purchaseOrderItemMapper.getPurchaseOrderItemsResponse(purchaseOrder.getPurchaseOrderItems()))
                .build();
    }
}
