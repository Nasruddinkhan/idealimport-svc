package ca.com.idealimport.service.tax.entity;

import ca.com.idealimport.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "tax")
public class Tax  extends AuditableEntity implements Serializable {
    @Id
    @Column(name = "tax_id", length = 36)
    private String taxId;

    @Column(name = "tax_scheme_name", length = 3)
    private String taxSchemeName;

    @Column(name = "tax_name_1", length = 3)
    private String taxName1;

    @Column(name = "tax_name_2", length = 3)
    private String taxName2;

    @Column(name = "tax_rate_1")
    private BigDecimal taxRate1;

    @Column(name = "tax_rate_2")
    private BigDecimal taxRate2;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
