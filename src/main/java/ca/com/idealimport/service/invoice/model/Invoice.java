package ca.com.idealimport.service.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {
    private String soldTo;
    private String soldToAddress;
    private String invoiceDate;
    private String invoiceNumber;
    private String customerOrder;
    private String terms;
    private String via;
    private String refNo;
    private List<InvoiceItem> items;
    private BigDecimal subTotal;
    private BigDecimal gst;
    private BigDecimal qst;
    private BigDecimal total;
    private String remarks;
    private String shippingDate;

}