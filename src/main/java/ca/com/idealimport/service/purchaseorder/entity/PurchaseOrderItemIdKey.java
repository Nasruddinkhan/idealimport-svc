package ca.com.idealimport.service.purchaseorder.entity;

import ca.com.idealimport.service.party.entity.Party;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PurchaseOrderItemIdKey implements Serializable {

    @Column(name = "purchase_order_item_id")
    private String purchaseOrderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;
}