package ca.com.idealimport.service.purchaseorder.entity.dto;

import ca.com.idealimport.service.party.entity.Party;
import ca.com.idealimport.service.purchaseorder.entity.PurchaseOrderItem;
import lombok.Builder;

@Builder
public record UpdatePurchaseOrderBean(
        String itemCode,
        Party party,
        PurchaseOrderItem orderItem) {
}
