package ca.com.idealimport.service.invoice.service;

import ca.com.idealimport.service.invoice.model.Invoice;
import ca.com.idealimport.service.invoice.model.InvoiceItem;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class InvoiceService {

    public byte[] createInvoice(Invoice invoice) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);
        DeviceRgb backgroundColor = new DeviceRgb(230, 230, 250);
        PdfPage page = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page);
        canvas.saveState()
                .setFillColor(backgroundColor)
                .rectangle(0, 0, pdfDoc.getDefaultPageSize().getWidth(), pdfDoc.getDefaultPageSize().getHeight())
                .fill()
                .restoreState();
        ClassPathResource resource = new ClassPathResource("static/logo.jpg");
        try (InputStream is = resource.getInputStream()) {
            ImageData imageData = ImageDataFactory.create(is.readAllBytes());
            Image logo = new Image(imageData).scaleToFit(100, 50);
            document.add(logo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.add(new Paragraph("INVOICE")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("IDEAL IMPORTS / 9801405 CANADA INC.")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("8850 AVE DU PARC SUITE - 100 MONTREAL, QC. H2N 1Y6")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("PH: (514) 383-2446")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("GST: 764238093    QST: 1223937779")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Customer Details
        document.add(new Paragraph("Vendu a / Sold to")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setBold());
        document.add(new Paragraph(invoice.getSoldTo())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12));
        document.add(new Paragraph(invoice.getSoldToAddress())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(20));

        // Invoice Details
        document.add(new Paragraph("Facture / Invoice: " + invoice.getInvoiceNumber())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12));
        document.add(new Paragraph("Date: " + invoice.getInvoiceDate())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12));
        document.add(new Paragraph("Commande du client / Customer's order: " + invoice.getCustomerOrder())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12));
        document.add(new Paragraph("Conditions / Terms: " + invoice.getTerms())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12));
        document.add(new Paragraph("VIA: " + invoice.getVia())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12));
        document.add(new Paragraph("REF NO: " + invoice.getRefNo())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(20));

        // Items Table
        float[] columnWidths = {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1.5f, 1.5f, 1.5f}; // Adjusted column widths to accommodate SIZE columns
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Set table borders
        table.setMarginBottom(20);
        table.setFontSize(12);
        table.setBorder(new SolidBorder(ColorConstants.BLACK, 1));

        table.addHeaderCell("ITEM").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("COLOR").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("XS").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("S").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("M").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("L").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("XL").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("XXL").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("XXXL").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("MIXED").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("QTY").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("RATE").setBackgroundColor(ColorConstants.GRAY);
        table.addHeaderCell("AMOUNT").setBackgroundColor(ColorConstants.GRAY);

        for (InvoiceItem item : invoice.getItems()) {
            table.addCell(item.getItemId());
            table.addCell(item.getColor());
            table.addCell(item.getXs() != null ? item.getXs().toString() : "");
            table.addCell(item.getS() != null ? item.getS().toString() : "");
            table.addCell(item.getM() != null ? item.getM().toString() : "");
            table.addCell(item.getL() != null ? item.getL().toString() : "");
            table.addCell(item.getXl() != null ? item.getXl().toString() : "");
            table.addCell(item.getXxl() != null ? item.getXxl().toString() : "");
            table.addCell(item.getXxxl() != null ? item.getXxxl().toString() : "");
            table.addCell(item.getMixed() != null ? item.getMixed().toString() : "");
            int quantity = (item.getXs() != null ? item.getXs() : 0) +
                    (item.getS() != null ? item.getS() : 0) +
                    (item.getM() != null ? item.getM() : 0) +
                    (item.getL() != null ? item.getL() : 0) +
                    (item.getXl() != null ? item.getXl() : 0) +
                    (item.getXxl() != null ? item.getXxl() : 0) +
                    (item.getXxxl() != null ? item.getXxxl() : 0) +
                    (item.getMixed() != null ? item.getMixed() : 0);
            table.addCell(String.valueOf(quantity));
            table.addCell(item.getRate().toString());
            table.addCell(item.getAmount().toString());
        }

        document.add(table);

        // Totals
        document.add(new Paragraph("Sub-Total: $" + invoice.getSubTotal())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("GST (5.0000 %): $" + invoice.getGst())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("QST (9.9750 %): $" + invoice.getQst())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Total: $" + invoice.getTotal())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(20));

        document.add(new Paragraph("Remarks: " + invoice.getRemarks())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(10));
        document.add(new Paragraph("Shipping Date: " + invoice.getShippingDate())
                .setFont(PdfFontFactory.createFont())
                .setFontSize(12)
                .setMarginBottom(10));

        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
