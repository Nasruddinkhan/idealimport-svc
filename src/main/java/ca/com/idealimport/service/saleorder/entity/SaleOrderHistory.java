package ca.com.idealimport.service.saleorder.entity;


import ca.com.idealimport.common.enums.SaleOrderStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_order_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOrderHistory {

    @Id
    @Column(name = "sale_order_history_id", length = 36)
    private String saleOrderHistoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private SaleOrderStatusEnum orderStatus;

    @Column(name = "sale_order_id", length = 36)
    private String saleOrderId;

}
