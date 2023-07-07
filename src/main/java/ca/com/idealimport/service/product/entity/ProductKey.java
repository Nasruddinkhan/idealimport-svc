package ca.com.idealimport.service.product.entity;

import ca.com.idealimport.service.party.entity.Party;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductKey implements Serializable {


    @Column(name = "product_id")
    private UUID productId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

}
