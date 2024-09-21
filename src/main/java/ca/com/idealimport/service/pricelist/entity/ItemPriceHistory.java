package ca.com.idealimport.service.pricelist.entity;


import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.customer.entity.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "item_price_history")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemPriceHistory extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_price_history_id")
    private Long itemPriceHistoryId;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_price_id")
    private Long itemPriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "p_style",  length = 50)
    private String pStyle;

    @Column(name = "style",  length = 50)
    private String style;

    @Column(name = "item_id", length = 50)
    private String itemId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "previous_price")
    private BigDecimal previousPrice;

    @Column(name = "party",  length = 50)
    private String partyName;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

}
