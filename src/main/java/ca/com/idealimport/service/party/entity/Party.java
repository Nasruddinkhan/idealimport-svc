package ca.com.idealimport.service.party.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "party")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Party extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long partyId;

    @Column(name = "full_name", length = 60, unique = true)
    private String fullName;
    @Column(name = "short_name", length = 20)
    private String shortName;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
