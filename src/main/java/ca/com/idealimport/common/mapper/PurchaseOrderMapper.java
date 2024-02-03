package ca.com.idealimport.common.mapper;

import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderIdKey;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderDto;
import ca.com.idealimport.service.purchaseorder.entity.dto.PurchaseOrderResponse;
import ca.com.idealimport.service.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseOrderMapper {

    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    public PurchaseOrderIdKey getPurchaseOrderIdKey(String uuId, Party party) {
        return PurchaseOrderIdKey.builder()
                .purchaseOrderId(uuId)
                .party(party)
                .build();
    }

    public PurchaseOrder getPurchaseOrderDtoToEntity(PurchaseOrderIdKey purchaseOrderIdKey, PurchaseOrderDto purchaseOrderDto, User user) {

        return PurchaseOrder.builder()
                .purchaseOrderId(purchaseOrderIdKey)
                .itemCode(purchaseOrderDto.addPurchaseOrderDto().itemCode())
                .orderDate(purchaseOrderDto.orderDate())
                .departureDate(purchaseOrderDto.departureDate())
                .containerName(purchaseOrderDto.containerName())
                .totalQuantity(purchaseOrderDto.addPurchaseOrderDto().totalQuantity())
                .lotNumber(purchaseOrderDto.lotNumber())
                .shippingStatus(purchaseOrderDto.addPurchaseOrderDto().status())
                .isActive(Boolean.TRUE)
                .user(user)
                .build();
    }

    public PurchaseOrderResponse convertProductItemToProductCreationResponse(String productId) {
        return PurchaseOrderResponse.builder()
                .msg(String.format("create the purchase successfully with the order id %s", productId))
                .build();
    }
}
