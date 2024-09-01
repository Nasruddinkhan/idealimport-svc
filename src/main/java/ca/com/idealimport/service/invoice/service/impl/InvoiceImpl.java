package ca.com.idealimport.service.invoice.service.impl;

import ca.com.idealimport.common.util.CommonUtils;
import ca.com.idealimport.service.invoice.model.SOInvoiceItem;
import ca.com.idealimport.service.invoice.service.InvoiceService;
import ca.com.idealimport.service.product.control.ProductControl;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.service.ProductService;
import ca.com.idealimport.service.saleorder.entity.Amount;
import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import ca.com.idealimport.service.saleorder.entity.SaleOrderItem;
import ca.com.idealimport.service.saleorder.service.SaleOrderService;
import ca.com.idealimport.service.tax.entity.Tax;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceImpl implements InvoiceService {
    private final SaleOrderService saleOrderService;
    private final ProductService productService;
    private final ResourceLoader resourceLoader;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public byte[] createInvoice(String orderId) throws IOException, JRException {
        SaleOrder saleOrder = saleOrderService.getSaleOrder(orderId);
        List<SOInvoiceItem> reducedItems = saleOrder.getItems().stream()
                .map(this::getSoInvoiceItem)
                .collect(Collectors.groupingBy(SOInvoiceItem::getItem))
                .values().stream()
                .map(this::getSoInvoiceItem)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        reducedItems.forEach(System.out::println);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reducedItems);

        Amount amount = saleOrder.getAmounts();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoice",  String.format("INV-", saleOrder.getSaleOrderId().split("-")[1]));
        parameters.put("orderDate", saleOrder.getCreatedDate().toString());
        parameters.put("fromAddress", saleOrder.getCustomer().getAddress());
        parameters.put("customerAlais",String.format("%s %s", saleOrder.getCustomer().getCustomerName(), saleOrder.getSaleOrderId()));
        parameters.put("customerAddressAlais", saleOrder.getCustomer().getAddress());
        parameters.put("via", Optional.ofNullable(saleOrder.getSaleOrderInfo().getVia()).orElse("NA"));
        parameters.put("ref",  Optional.ofNullable(saleOrder.getSaleOrderInfo().getRef()).orElse("NA"));
        parameters.put("listOfOrder", dataSource);
        parameters.put("discount",amount.getDiscount());
        parameters.put("subTotal",amount.getSubTotal().toString());
        final Tax tax = amount.getTax();
        final String firstTax =  Optional.ofNullable(tax)
                .map(e-> String.format("%s (%.4f%%)", tax.getTaxName1(), tax.getTaxRate1()))
                .orElse("NA");
        final String secTax =  Optional.ofNullable(tax)
                .map(e-> String.format("%s (%.4f%%)", tax.getTaxRate2(), tax.getTaxRate2()))
                .orElse("NA");
        final String firstTaxValue = Optional.ofNullable(tax)
                .map(e-> String.format("$ %s",
                                amount.getSubTotal().multiply(tax.getTaxRate1()).divide(new BigDecimal("100"))))
                .orElse("NA");
        String secTaxValue = Optional.ofNullable(tax)
                .map(e-> String.format("$ %s",
                        amount.getSubTotal().multiply(tax.getTaxRate1()).divide(new BigDecimal("100"))))
                .orElse("NA");
        parameters.put("firstTax",firstTax);
        parameters.put("secTax",secTax);
        parameters.put("firstTaxValue",firstTaxValue);
        parameters.put("secTaxValue",secTaxValue);
        parameters.put("grandTotal",amount.getSubTotal());
        parameters.put("shippingDate","2024/05/09");
        parameters.put("remarks",saleOrder.getSaleOrderInfo().getRemarks());
        try(InputStream inputStream = CommonUtils.readFileFromResources("classpath:templates/reports/sale-order.jrxml", resourceLoader)){
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    private  SOInvoiceItem getSoInvoiceItem(List<SOInvoiceItem> items) {
        return items.stream().reduce((s1, s2) -> SOInvoiceItem.builder()
                .item(s1.getItem())
                .party(s1.getParty())
                .qty(s1.getQty() + s2.getQty())
                .rate(s1.getRate())
                .totalAmount(s1.getTotalAmount().add(s2.getTotalAmount()))
                .style(s1.getStyle())
                .newStyle(s1.getNewStyle())
                .build()).orElse(null);
    }

    private SOInvoiceItem getSoInvoiceItem(SaleOrderItem e) {
        Product product = productService.findByProductKeyPartyAndItemCode(e.getParty(), e.getItemCode());
        return SOInvoiceItem.builder()
                .item(product.getItemCode())
                .party(e.getParty().getFullName())
                .qty(e.getOrderItem().getQty())
                .rate(e.getOrderItem().getUnitPrice())
                .totalAmount(BigDecimal.valueOf(e.getOrderItem().getQty()).multiply(e.getOrderItem().getUnitPrice()))
                .style(product.getStyle())
                .newStyle(product.getPackingColors())
                .build();
    }
}
