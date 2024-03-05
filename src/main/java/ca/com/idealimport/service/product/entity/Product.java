package ca.com.idealimport.service.product.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends AuditableEntity implements Serializable {

    @EmbeddedId
    private ProductKey productKey;
    @Column(name = "item_code", length = 50, unique = true)
    private String itemCode;
    @Column(name = "contents",columnDefinition = "TEXT")
    private String contents;
    @Column(name = "style", length = 100)
    private String style;
    @Column(name = "label", length = 100)
    private String label;
    @Column(name = "weight", length = 100)
    private String weight;
    @Column(name = "packing_poly_bag", length = 100)
    private String packingPolyBag;
    @Column(name = "packing_box", length = 100)
    private String packingBox;
    @Column(name = "packing_colors", length = 100)
    private String packingColors;
    @Column(name = "quantity_in_hand")
    private Integer quantityInHand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductItem> productItems;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
