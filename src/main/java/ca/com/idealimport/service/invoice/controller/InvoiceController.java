package ca.com.idealimport.service.invoice.controller;

import ca.com.idealimport.config.logging.LogApiTime;
import ca.com.idealimport.service.invoice.model.SOOrderForm;
import ca.com.idealimport.service.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/invoice/v1")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @LogApiTime
    @GetMapping("/sale-order/{orderId}")
    public byte[] createInvoice(@PathVariable String orderId) throws IOException, JRException {
       return  invoiceService.createInvoice(orderId);
    }

    @LogApiTime
    @GetMapping("/sale-order-form/{orderId}")
    public ResponseEntity<byte[]> createOrderFormInvoice(@PathVariable String orderId) throws IOException, JRException {
        return  ResponseEntity.ok(invoiceService.createOrderInvoice(orderId));
    }
}
