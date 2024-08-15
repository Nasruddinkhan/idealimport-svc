package ca.com.idealimport.service.saleorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import ca.com.idealimport.service.customer.entity.Customer;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

import static ca.com.idealimport.common.util.SaleOrderPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER;
import static ca.com.idealimport.common.util.SaleOrderPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER;
import static org.hibernate.id.OptimizableGenerator.INCREMENT_PARAM;

@Entity
@Table(name = "sale_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOrder  extends AuditableEntity {
    public static final String TEN_DIGIT = "%010d";
    @Id
    @Column(name = "sale_order_id", length = 36)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SO_SEQ")
    @GenericGenerator(
            name = "SO_SEQ",
            strategy = "ca.com.idealimport.common.util.SaleOrderPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = INCREMENT_PARAM, value = "1"),
                    @Parameter(name = VALUE_PREFIX_PARAMETER, value = "SO-"),
                    @Parameter(name = NUMBER_FORMAT_PARAMETER, value = TEN_DIGIT)})
    private String saleOrderId;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "amount_id")
    private Amount amounts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "sale_order_id")
    private List<SaleOrderItem> items;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "sale_order_info_id")
    private SaleOrderInfo saleOrderInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "tracking_id", length = 15, unique = true, updatable = false)
    private String trackingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private SaleOrderStatusEnum orderStatus;

}
