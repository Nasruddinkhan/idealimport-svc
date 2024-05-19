package ca.com.idealimport.service.saleorder.entity;

import ca.com.idealimport.service.party.entity.Party;
import jakarta.persistence.CascadeType;
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

@Entity
@Table(name = "sale_order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOrderItem {

    @Id
    @Column(name = "sale_order_item_id", length = 36)
    private String saleOrderItemId;

    @Column(name = "item_code", length = 50)
    private String itemCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "party_id")
    private Party party;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

}
