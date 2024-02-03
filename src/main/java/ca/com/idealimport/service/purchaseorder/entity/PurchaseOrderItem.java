package ca.com.idealimport.service.purchaseorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "purchase_order_item")
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderItem extends AuditableEntity implements Serializable {

    @Id
    @Column(name = "purchase_order_item_id", length = 36)
    private String purchaseOrderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "purchase_order_id", referencedColumnName = "purchase_order_id"),
            @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    })
    private PurchaseOrder purchaseOrder;

    @Column(name = "details", length = 1000)
    private String details;

    @Column(name = "xs", columnDefinition = "INTEGER")
    private Integer xs;

    @Column(name = "s", columnDefinition = "INTEGER")
    private Integer s;

    @Column(name = "m", columnDefinition = "INTEGER")
    private Integer m;

    @Column(name = "l", columnDefinition = "INTEGER")
    private Integer l;

    @Column(name = "xl", columnDefinition = "INTEGER")
    private Integer xl;

    @Column(name = "xxl", columnDefinition = "INTEGER")
    private Integer xxl;

    @Column(name = "xxxl", columnDefinition = "INTEGER")
    private Integer xxxl;

    @Column(name = "mixed", columnDefinition = "INTEGER")
    private Integer mixed;

    @Column(name = "sub_total", columnDefinition = "INTEGER")
    private Integer subTotal;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
