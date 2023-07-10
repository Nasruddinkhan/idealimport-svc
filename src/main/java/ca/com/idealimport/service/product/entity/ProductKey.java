package ca.com.idealimport.service.product.entity;

import ca.com.idealimport.service.party.entity.Party;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

public class ProductKey implements Serializable {


    @Column(name = "product_id")
    private String productId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

}
