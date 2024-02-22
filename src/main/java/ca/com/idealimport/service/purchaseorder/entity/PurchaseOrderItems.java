package ca.com.idealimport.service.purchaseorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrderItems extends AuditableEntity implements Serializable {
    @EmbeddedId
    private PurchaseOrderItemIdKey purchaseOrderItemIdKey;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "item_code", length = 50)
    private String itemCode;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrderItems", fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> purchaseOrderItem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}