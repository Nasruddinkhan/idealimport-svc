package ca.com.idealimport.service.saleorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_order_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOrderInfo {
    @Id
    @Column(name = "sale_order_info_id", length = 36)
    private String saleOrderInfoId;

    @Column(name = "sale_order_by", length = 50)
    private String orderBy;

    @Column(name = "order_no", length = 50)
    private String orderNo;

    @Column(name = "via", length = 20)
    private String via;
    @Column(name = "ref", length = 20)
    private String ref;

    @Column(name = "remarks", length = 250)
    private String remarks;

    @Column(name = "conditions", length = 250)
    private String conditions;
}
