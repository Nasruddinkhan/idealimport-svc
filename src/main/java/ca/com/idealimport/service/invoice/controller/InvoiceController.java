package ca.com.idealimport.service.invoice.controller;

import ca.com.idealimport.service.invoice.model.Invoice;
import ca.com.idealimport.service.invoice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    private static List<InvoiceItem> getInvoiceItems() {
        List<InvoiceItem> items = new ArrayList<>();
        InvoiceItem item1 = new InvoiceItem();
        item1.setItemId("Item001");
        item1.setColor("Red");
        item1.setXs(10);
        item1.setS(20);
        item1.setM(15);
        item1.setL(5);
        item1.setXl(10);
        item1.setXxl(8);
        item1.setXxxl(2);
        item1.setMixed(0);
        item1.setRate(new BigDecimal("100.00"));
        item1.setAmount(new BigDecimal("7000.00"));
        items.add(item1);
        return items;
    }

    @GetMapping(value = "/create", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createInvoice() {
        try {
            Invoice invoice = new Invoice();
            invoice.setSoldTo("Customer Name");
            invoice.setSoldToAddress("Customer Address");
            invoice.setInvoiceNumber("INV12345");
            invoice.setInvoiceDate("2024-05-20");
            invoice.setCustomerOrder("Order123");
            invoice.setTerms("Net 30");
            invoice.setVia("UPS");
            invoice.setRefNo("Ref123");

            List<InvoiceItem> items = getInvoiceItems();

            invoice.setItems(items);
            invoice.setSubTotal(new BigDecimal("7000.00"));
            invoice.setGst(new BigDecimal("350.00"));
            invoice.setQst(new BigDecimal("697.50"));
            invoice.setTotal(new BigDecimal("8047.50"));
            invoice.setRemarks("20 BOXES PICKED UP BY HAROUT");
            invoice.setShippingDate("2024/05/09");

            byte[] pdf = invoiceService.createInvoice(invoice);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdf);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
