package ca.com.idealimport.service.purchaseorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.purchaseorder.entity.enums.ShippingStatus;
import ca.com.idealimport.service.users.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "purchase_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class PurchaseOrder extends AuditableEntity implements Serializable {

    @EmbeddedId
    private PurchaseOrderIdKey purchaseOrderId;

    @Column(name = "lot_number", length = 20)
    private String lotNumber;

    @Column(name = "container_name", length = 20)
    private String containerName;

    @Column(name = "order_date")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate orderDate;

    @Column(name = "departure_date")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate departureDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status")
    private ShippingStatus shippingStatus;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> purchaseOrderItems;

    @Column(name = "item_code", length = 50)
    private String itemCode;

    @Column(name = "totalQuantity")
    private Integer totalQuantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
