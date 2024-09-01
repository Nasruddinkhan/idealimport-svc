package ca.com.idealimport.service.invoice.service.impl;

import ca.com.idealimport.service.invoice.model.SOInvoiceItem;
import ca.com.idealimport.service.invoice.service.InvoiceService;
import ca.com.idealimport.service.product.control.ProductControl;
import ca.com.idealimport.service.product.entity.Product;
import ca.com.idealimport.service.product.service.ProductService;
import ca.com.idealimport.service.saleorder.entity.SaleOrder;
import ca.com.idealimport.service.saleorder.entity.SaleOrderItem;
import ca.com.idealimport.service.saleorder.service.SaleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceImpl implements InvoiceService {
    private final SaleOrderService saleOrderService;
    private final ProductService productService;

    @Override
    public byte[] createInvoice(String orderId) {
        SaleOrder saleOrder = saleOrderService.getSaleOrder(orderId);
        List<SOInvoiceItem> reducedItems = saleOrder.getItems().stream()
                .map(this::getSoInvoiceItem)
                .collect(Collectors.groupingBy(SOInvoiceItem::getItem))
                .values().stream()
                .map(this::getSoInvoiceItem)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        reducedItems.forEach(System.out::println);
        return new byte[0];
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
