package ca.com.idealimport.service.invoice.controller;

import ca.com.idealimport.service.invoice.service.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/sale-order/{orderId}")
    public byte[] createInvoice(@PathVariable String orderId) throws IOException, JRException {
       return  invoiceService.createInvoice(orderId);
    }

}
