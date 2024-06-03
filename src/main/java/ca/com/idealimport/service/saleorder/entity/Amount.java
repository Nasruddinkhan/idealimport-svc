package ca.com.idealimport.service.saleorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.tax.entity.Tax;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order-amount")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Amount extends AuditableEntity {

    @Id
    @Column(name = "amount_id", length = 36)
    private String amountId;
    private BigDecimal discount;
    private BigDecimal subTotal;
    private BigDecimal totalAmount;
    private BigDecimal paid;
    private BigDecimal balance;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "tax_id")
    private Tax tax;

}
