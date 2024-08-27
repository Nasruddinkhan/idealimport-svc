package ca.com.idealimport.service.invoice.controller;

import ca.com.idealimport.service.invoice.model.InvoiceItem;
import ca.com.idealimport.service.invoice.model.SOInvoiceItem;
import ca.com.idealimport.service.invoice.service.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ResourceLoader resourceLoader;

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
    public void createInvoice(HttpServletResponse response) throws IOException, JRException {
        List<SOInvoiceItem> orderItems = fetchOrderItems(); // Replace with your data fetching logic

        // Create a JRBeanCollectionDataSource with your list of data
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderItems);

        // Load the jrxml file
        Resource resource = resourceLoader.getResource("classpath:templates/reports/sale-order.jrxml");
        InputStream inputStream = resource.getInputStream();

        // Compile the JasperReport
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Hardcoded parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoice", "INV-2024-001");
        parameters.put("orderDate", "2024-08-24");
        parameters.put("fromAddress", "1234 Main St, Springfield, USA");
        parameters.put("customerAlais", "John Doe");
        parameters.put("customerAddressAlais", "7890 Elm St, Shelbyville, USA");
        parameters.put("via", "FedEx");
        parameters.put("ref", "Ref-001");
        parameters.put("listOfOrder", dataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=sale-order.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    }

    private List<SOInvoiceItem> fetchOrderItems() {
        // Replace this with your actual logic to fetch order items from the database
        return List.of(
                new SOInvoiceItem("Party A", "Item 1", "Style A", 10, new BigDecimal("20.00"), new BigDecimal("200.00")),
                new SOInvoiceItem("Party B", "Item 2", "Style B", 5, new BigDecimal("15.00"), new BigDecimal("75.00"))
        );
    }
}
