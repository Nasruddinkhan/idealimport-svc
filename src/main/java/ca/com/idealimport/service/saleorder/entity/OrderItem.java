package ca.com.idealimport.service.saleorder.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.product.entity.ProductItem;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem extends AuditableEntity {
    @Id
    @Column(name = "order_item_id", length = 36)
    private String orderItemId;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "xs", columnDefinition = "INT")
    private Integer xs;

    @Column(name = "s", columnDefinition = "INT")
    private Integer s;

    @Column(name = "m", columnDefinition = "INT")
    private Integer m;

    @Column(name = "l", columnDefinition = "INT")
    private Integer l;

    @Column(name = "xl", columnDefinition = "INT")
    private Integer xl;

    @Column(name = "xxl", columnDefinition = "INT")
    private Integer xxl;

    @Column(name = "xxxl", columnDefinition = "INT")
    private Integer xxxl;

    @Column(name = "mixed", columnDefinition = "INT")
    private Integer mixed;

    @Column(name = "sub_total", columnDefinition = "INT")
    private Integer subTotal;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItem productItemId;
}
