package ca.com.idealimport.service.customer.entity;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.entity.AuditableEntity;
import ca.com.idealimport.common.util.DropDownDtoListConverter;
import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name", length = 150)
    private String customerName;

    @Column(name = "company_name", length = 150)
    private String companyName;
    private BigDecimal balance;
    @Column(name = "email", length = 100, unique = true)
    private String email;
    @Column(name = "phone_no", length = 20, unique = true)
    private String phoneNo;
    @Column(name = "fax_no", length = 50)
    private String faxNo;

    @Column(name = "address", columnDefinition = "nvarchar(1000)")
    private String address;
    @Column(name = "remark", columnDefinition = "nvarchar(1000)")
    private String remarks;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // need to change customer have mutiple party
    @Column(name = "party_ids", columnDefinition = "nvarchar(1000)")
    @Convert(converter = DropDownDtoListConverter.class)
    private List<DropDownDto> parties;
}
