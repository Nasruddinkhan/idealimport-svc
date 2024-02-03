package ca.com.idealimport.common.mapper;

import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrder;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItem;
import ca.com.idealimport.service.purchaseorder.entity.dto.AddPurchaseOrderItemDto;
import ca.com.idealimport.service.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PurchaseOrderItemMapper {
    public List<PurchaseOrderItem> convertPurchaseOrderItemDtosToEntity(PurchaseOrder purchaseOrder,
                                                                        List<AddPurchaseOrderItemDto> purchaseOrderItems,
                                                                        User user) {
        return purchaseOrderItems.stream().map(e -> convertPurchaseOrderItemToEntity(purchaseOrder, e, user)).toList();

    }

    private PurchaseOrderItem convertPurchaseOrderItemToEntity(PurchaseOrder purchaseOrder,
                                                               AddPurchaseOrderItemDto purchaseOrderItems,
                                                               User user) {
        return PurchaseOrderItem.builder()
                .purchaseOrder(purchaseOrder)
                .purchaseOrderItemId(CommonUtils.getUUID(purchaseOrderItems.addPurchaseOrderItemId()))
                .s(purchaseOrderItems.s())
                .l(purchaseOrderItems.l())
                .xl(purchaseOrderItems.xl())
                .m(purchaseOrderItems.m())
                .xs(purchaseOrderItems.xs())
                .xxl(purchaseOrderItems.xxl())
                .xxxl(purchaseOrderItems.xxxl())
                .mixed(purchaseOrderItems.mixed())
                .details(purchaseOrderItems.details())
                .user(user)
                .subTotal(purchaseOrderItems.subTotal())
                .isActive(Boolean.TRUE)
                .build();
    }
}
