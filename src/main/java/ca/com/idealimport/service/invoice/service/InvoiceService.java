package ca.com.idealimport.service.invoice.service;

import ca.com.idealimport.service.invoice.model.SOOrderForm;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;

public interface InvoiceService {

    byte[] createInvoice(String orderId) throws IOException, JRException;

    List<SOOrderForm> createOrderInvoice(String orderId) throws IOException, JRException;

}
