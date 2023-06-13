package ca.com.idealimport.service.customer.entity;

import ca.com.idealimport.service.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

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
}
