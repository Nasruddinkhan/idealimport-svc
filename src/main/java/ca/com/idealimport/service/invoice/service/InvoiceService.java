package ca.com.idealimport.service.invoice.service;

import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface InvoiceService {

    byte[] createInvoice(String orderId) throws IOException, JRException;
}
