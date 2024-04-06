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

import java.io.Serializable;

@Entity
@Table(name = "sale-order-status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOrderStatus extends AuditableEntity implements Serializable {
    @Id
    @Column(name = "sale_order_status_id", length = 36)
    private String saleOrderStatusId;

    @Column(name = "name", length = 60, unique = true)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean isActive;

}
