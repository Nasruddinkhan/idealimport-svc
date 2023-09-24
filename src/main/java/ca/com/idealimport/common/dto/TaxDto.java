package ca.com.idealimport.common.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaxDto {
    private String taxId;
    private String taxSchemeName;
    private String taxName1;
    private String taxName2;
    private BigDecimal taxRate1;
    private BigDecimal taxRate2;
}
