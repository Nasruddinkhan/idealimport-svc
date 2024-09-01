package ca.com.idealimport.service.invoice.controller;

import ca.com.idealimport.service.invoice.model.InvoiceItem;
import ca.com.idealimport.service.invoice.model.SOInvoiceItem;
import ca.com.idealimport.service.invoice.service.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JREmptyDataSource;
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
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/sale-order/{orderId}")
    public byte[] createInvoice(@PathVariable String orderId, HttpServletResponse response) throws IOException, JRException {
        List<SOInvoiceItem> orderItems = fetchOrderItems(); // Replace with your data fetching logic
        final byte[] orderInvoice = invoiceService.createInvoice(orderId);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderItems);

        Resource resource = resourceLoader.getResource("classpath:templates/reports/sale-order.jrxml");
        InputStream inputStream = resource.getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoice", "INV-2024-001");
        parameters.put("orderDate", "2024-08-24");
        parameters.put("fromAddress", "1234 Main St, Springfield, USA");
        parameters.put("customerAlais", "John Doe SO-00001");
        parameters.put("customerAddressAlais", "7890 Elm St, Shelbyville, USA");
        parameters.put("via", "FedEx");
        parameters.put("ref", "Ref-001");
        parameters.put("listOfOrder", dataSource);
        parameters.put("discount", new BigDecimal("50"));
        parameters.put("subTotal","100");
        parameters.put("firstTax","GST (5.0000 %)");
        parameters.put("secTax","GST (5.0000 %)");
        parameters.put("firstTaxValue","162.00");
        parameters.put("secTaxValue","323.19");
        parameters.put("grandTotal",new BigDecimal("3725.19"));
        parameters.put("shippingDate","2024/05/09");
        parameters.put("remarks","20 BOXES PICKED UP BY HAROUT");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=sale-order.pdf");
       return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    private List<SOInvoiceItem> fetchOrderItems() {
        // Replace this with your actual logic to fetch order items from the database
        return List.of(
                new SOInvoiceItem("Party A", "Item 1", "Style A", 10, new BigDecimal("20.00"), new BigDecimal("200.00"),"Style A"),
                new SOInvoiceItem("Party B", "Item 2", "Style B", 5, new BigDecimal("15.00"), new BigDecimal("75.00"),"Style B"),
                new SOInvoiceItem("Party C", "Item 3", "Style C", 8, new BigDecimal("18.00"), new BigDecimal("144.00"),"Style C"),
                new SOInvoiceItem("Party D", "Item 4", "Style D", 12, new BigDecimal("22.00"), new BigDecimal("264.00"),"Style D"),
                new SOInvoiceItem("Party E", "Item 5", "Style E", 20, new BigDecimal("25.00"), new BigDecimal("500.00"),"Style E"),
                new SOInvoiceItem("Party F", "Item 6", "Style F", 15, new BigDecimal("30.00"), new BigDecimal("450.00"),"Style F")
               // new SOInvoiceItem("Party G", "Item 7", "Style G", 7, new BigDecimal("12.00"), new BigDecimal("84.00")),
              //  new SOInvoiceItem("Party H", "Item 8", "Style H", 6, new BigDecimal("10.00"), new BigDecimal("60.00")),
              //  new SOInvoiceItem("Party I", "Item 9", "Style I", 14, new BigDecimal("28.00"), new BigDecimal("392.00"))
//                new SOInvoiceItem("Party J", "Item 10", "Style J", 11, new BigDecimal("16.00"), new BigDecimal("176.00")),
//                new SOInvoiceItem("Party K", "Item 11", "Style K", 13, new BigDecimal("24.00"), new BigDecimal("312.00")),
//                new SOInvoiceItem("Party L", "Item 12", "Style L", 9, new BigDecimal("19.00"), new BigDecimal("171.00")),
//                new SOInvoiceItem("Party M", "Item 13", "Style M", 16, new BigDecimal("29.00"), new BigDecimal("464.00")),
//                new SOInvoiceItem("Party N", "Item 14", "Style N", 4, new BigDecimal("9.00"), new BigDecimal("36.00")),
//                new SOInvoiceItem("Party O", "Item 15", "Style O", 3, new BigDecimal("8.00"), new BigDecimal("24.00")),
//                new SOInvoiceItem("Party P", "Item 16", "Style P", 2, new BigDecimal("7.00"), new BigDecimal("14.00")),
//                new SOInvoiceItem("Party Q", "Item 17", "Style Q", 18, new BigDecimal("32.00"), new BigDecimal("576.00")),
//                new SOInvoiceItem("Party R", "Item 18", "Style R", 1, new BigDecimal("6.00"), new BigDecimal("6.00")),
//                new SOInvoiceItem("Party S", "Item 19", "Style S", 25, new BigDecimal("35.00"), new BigDecimal("875.00")),
//                new SOInvoiceItem("Party T", "Item 20", "Style T", 22, new BigDecimal("33.00"), new BigDecimal("726.00")),
//                new SOInvoiceItem("Party U", "Item 21", "Style U", 17, new BigDecimal("31.00"), new BigDecimal("527.00")),
//                new SOInvoiceItem("Party V", "Item 22", "Style V", 19, new BigDecimal("34.00"), new BigDecimal("646.00")),
//                new SOInvoiceItem("Party W", "Item 23", "Style W", 21, new BigDecimal("36.00"), new BigDecimal("756.00")),
//                new SOInvoiceItem("Party X", "Item 24", "Style X", 23, new BigDecimal("37.00"), new BigDecimal("851.00")),
//                new SOInvoiceItem("Party Y", "Item 25", "Style Y", 24, new BigDecimal("38.00"), new BigDecimal("912.00")),
//                new SOInvoiceItem("Party Z", "Item 26", "Style Z", 26, new BigDecimal("40.00"), new BigDecimal("1040.00"))
     );
    }
}
