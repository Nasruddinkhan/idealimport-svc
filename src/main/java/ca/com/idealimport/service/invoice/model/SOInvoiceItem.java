package ca.com.idealimport.service.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SOInvoiceItem{
    private String party;    // Name of the party associated with the order item
    private String item;       // Name of the item
    private String style;          // Style of the item
    private int qty;          // Quantity of the item
    private BigDecimal rate;  // Price per unit
    private BigDecimal totalAmount;
    private String newStyle;
}
