package ca.com.idealimport.service.pricelist.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_price")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemPrice extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_price_id")
    private Long itemPriceId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_party_id", nullable = false)
    private CustomerParty customerParty;
}
