package ca.com.idealimport.service.saleorder.entity;

import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sale_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOrder {
    @Id
    @Column(name = "sale_order_id", length = 36)
    private String saleOrderId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_order_id")
    private List<Amount> amounts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_order_id")
    private List<SaleOrderItem> items;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_status_id")
    private SaleOrderStatus orderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_order_info_id")
    private SaleOrderInfo saleOrderInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "tracking_id", length = 15, unique = true, updatable = false)
    private String trackingId;

}
