package ca.com.idealimport.service.invoice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SOOrderForm {
    private String party;
    private String item;
    private String style;
    private String newStyle;
    private String color;
    private Integer xs;
    private Integer l;
    private Integer xl;
    private Integer xxl;
    private Integer xxxl;
    private Integer mixed;
    private Integer s;
    private Integer m;
    private int quantity;
    private BigDecimal rate;
    private BigDecimal amount;
}
