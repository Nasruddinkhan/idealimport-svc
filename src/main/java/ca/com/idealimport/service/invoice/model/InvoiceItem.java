package ca.com.idealimport.service.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceItem {
    private String itemId;
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
