package ca.com.idealimport.service.saleorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order-amount-history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOrderAmountAudit extends AuditableEntity {
    @Id
    @Column(name = "amount_history_id", length = 36)
    private String amountHistoryId;

    @Column(name = "sale_order_id", length = 36)
    private String saleOrderId;

    @Column(name = "amount_id", length = 36)
    private String amountId;
    @Column(name = "paid_amount")
    private BigDecimal paidAmount;
    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;
}
