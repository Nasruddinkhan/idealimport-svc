package ca.com.idealimport.service.purchaseorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

//@Table(name = "purchase_order_line_item")
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "purchase_order_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderItem extends AuditableEntity implements Serializable {

    @Id
    @Column(name = "purchase_order_line_item_id", length = 36)
    private String purchaseOrderLineItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "purchase_order_item_id", referencedColumnName = "purchase_order_item_id"),
            @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    })
    private PurchaseOrderItems purchaseOrderItems;

    @Column(name = "details", length = 1000)
    private String details;

    @Column(name = "xs", columnDefinition = "INT")
    private Integer xs;

    @Column(name = "s", columnDefinition = "INT")
    private Integer s;

    @Column(name = "m", columnDefinition = "INT")
    private Integer m;

    @Column(name = "l", columnDefinition = "INT")
    private Integer l;

    @Column(name = "xl", columnDefinition = "INT")
    private Integer xl;

    @Column(name = "xxl", columnDefinition = "INT")
    private Integer xxl;

    @Column(name = "xxxl", columnDefinition = "INT")
    private Integer xxxl;

    @Column(name = "mixed", columnDefinition = "INT")
    private Integer mixed;

    @Column(name = "sub_total", columnDefinition = "INT")
    private Integer subTotal;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
