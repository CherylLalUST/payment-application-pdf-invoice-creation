package com.example.payments.util;

import com.example.payments.model.Payment;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class InvoicePdfGenerator {

    public Payment generateInvoicePdf(Payment payment) throws IOException {


        // Create PDF
        String path = "invoice.pdf";
        // Initialize PDF Writer and Document
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        try {
            // Add invoice title without alignment
            Paragraph title = new Paragraph("Invoice")
                    .setFontSize(16)
                    .setBold();
            document.add(title);

            // Add space after title
            document.add(new Paragraph(" "));

            // Add invoice details
            document.add(new Paragraph("Invoice Number: " + payment.getInvoiceNumber()));
            document.add(new Paragraph("Payment Date: " + payment.getPaymentdate()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Company Details: " + payment.getCompanyDetails()));
            document.add(new Paragraph("Client Details: " + payment.getClientDetails()));
            document.add(new Paragraph("Purchase Order Number: " + payment.getPonumber()));
            document.add(new Paragraph("Target Bank Account: " + payment.getTargetBankAccount()));
            document.add(new Paragraph("Source Bank Account: " + payment.getSourceBankAccount()));
            document.add(new Paragraph(" "));

            // Item Details Section without text alignment
            Paragraph itemHeader = new Paragraph("Item Details")
                    .setFontSize(14)
                    .setBold();
            document.add(itemHeader);
            document.add(new Paragraph(" "));

            // Create a table with 4 columns (one for each label + one for values)
            Table table = new Table(4); // 4 columns (one for each label + one for values)

// Add headers (labels)
            table.addCell(new Cell().add(new Paragraph("Item Name")));
            table.addCell(new Cell().add(new Paragraph("Number of Days")));
            table.addCell(new Cell().add(new Paragraph("Price Per Day")));
            table.addCell(new Cell().add(new Paragraph("Total Price")));

// Add values in a single row
            table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getItemName()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getNoOfDays()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getPricePerDay()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getTotalPrice()))));

// Add the table to the document
            document.add(table);

// Add TDS percentage and Total Amount after TDS
            document.add(new Paragraph("TDS Percentage: " + payment.getTds()));
            document.add(new Paragraph("Total Amount after TDS: " + payment.getTotalAmountAfterTDS()));

            // Amount in words
            String amountInWords = NumberToWordsConverter.convert((long) payment.getTotalAmountAfterTDS());
            document.add(new Paragraph("Total Amount in Words: " + amountInWords));

            // Footer with signature
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Signature: __________________"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return payment;
    }
}
