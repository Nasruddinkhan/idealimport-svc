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

    @Id
    @Column(name = "purchase_order_id", length = 50)
    private String purchaseOrderId;

    @Column(name = "lot_number", length = 20, unique = true)
    private String lotNumber;

    @Column(name = "container_name", length = 20, unique = true)
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PurchaseOrderItems> purchaseOrderItems;
}
