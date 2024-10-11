package com.example.payments.util;

import com.example.payments.model.Payment;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.service.PaymentService;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class InvoicePdfGenerator {

    @Autowired
    private PaymentRepository paymentRepository;


    public Payment getInvoicePDF(String invoiceNumber) throws FileNotFoundException {
        Payment invoice = paymentRepository.findByInvoiceNumber(invoiceNumber);//.orElse(null);
        String path="invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        float threecol = 98f;
        float dwidth = 198f;
        float twocol = 285f;
        float twocol150 = twocol+150f;
        float twocolWidth[] = {twocol150,twocol};
        float fullwidth[]={dwidth*3};

        float fiveColumnWidth[] = {threecol, threecol, threecol,threecol,threecol};
        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(twocolWidth);
        table.addCell(new Cell().add(new Paragraph("Invoice")) // Use Paragraph here
                .setBorder(Border.NO_BORDER).setBold());
        Table nestedtable=new Table(new float[]{twocol/2,twocol/2});
        nestedtable.addCell(new Cell().add(new Paragraph("Invoice Number:"))
                .setBorder(Border.NO_BORDER).setBold());
        nestedtable.addCell(new Cell().add(new Paragraph(invoiceNumber))
                .setBorder(Border.NO_BORDER));
        nestedtable.addCell(new Cell().add(new Paragraph("Invoice date:"))
                .setBorder(Border.NO_BORDER).setBold());
        nestedtable.addCell(new Cell().add(new Paragraph(invoice.getPaymentdate().toString()))
                .setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(nestedtable).setBorder(Border.NO_BORDER));

        Border border = new SolidBorder(ColorConstants.GRAY,2f);

        Table divider = new Table(fullwidth);
        divider.setBorder(border);

        Table companyTable = new Table(twocolWidth);
        companyTable.addCell(new Cell().add(new Paragraph("Company Details")) // Use Paragraph here
                .setBorder(Border.NO_BORDER).setBold());
        companyTable.addCell(new Cell().add(new Paragraph(invoice.getCompanyDetails()))
                .setBorder(Border.NO_BORDER));

        Table clientTable=new Table(new float[]{twocol/2,twocol/2});
        clientTable.addCell(new Cell().add(new Paragraph("Client Details")) // Use Paragraph here
                .setBorder(Border.NO_BORDER).setBold());
        clientTable.addCell(new Cell().add(new Paragraph(invoice.getClientDetails()))
                .setBorder(Border.NO_BORDER));
        companyTable.addCell(new Cell().add(clientTable).setBorder(Border.NO_BORDER));

        Table companyBankTable=new Table(new float[]{twocol/2,twocol/2});
        companyBankTable.addCell(new Cell().add(new Paragraph("Company Bank Details")) // Use Paragraph here
                .setBorder(Border.NO_BORDER).setBold());
        companyBankTable.addCell(new Cell().add(new Paragraph(invoice.getTargetBankAccount()))
                .setBorder(Border.NO_BORDER));

        Table clientBankTable=new Table(new float[]{twocol/2,twocol/2});
        clientBankTable.addCell(new Cell().add(new Paragraph("Client Bank Details")) // Use Paragraph here
                .setBorder(Border.NO_BORDER).setBold());
        clientBankTable.addCell(new Cell().add(new Paragraph(invoice.getSourceBankAccount()))
                .setBorder(Border.NO_BORDER));

        //Product details
        Paragraph prodPara = new Paragraph("Product details");


        document.add(table);
        document.add(onesp);
        document.add(divider);
        //document.add(onesp);
        document.add(companyTable);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);
        document.add(companyBankTable);
        document.add(clientBankTable);
        document.add(onesp);
        document.add(divider);
        document.add(prodPara.setBold());

        Table fiveColumnTable1 = new Table(fiveColumnWidth);
        fiveColumnTable1.setBackgroundColor(ColorConstants.BLACK,0.7f);
        fiveColumnTable1.addCell(new Cell().add(new Paragraph("Date")) // Use Paragraph here
                .setBold().setFontColor(ColorConstants.WHITE));

        fiveColumnTable1.addCell(new Cell().add(new Paragraph("Item name")) // Use Paragraph here
                .setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER));

        fiveColumnTable1.addCell(new Cell().add(new Paragraph("Number of days")) // Use Paragraph here
                .setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER));
        fiveColumnTable1.addCell(new Cell().add(new Paragraph("Price per day")) // Use Paragraph here
                .setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER));

        fiveColumnTable1.addCell(new Cell().add(new Paragraph("Total price")) // Use Paragraph here
                .setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER));

        document.add(fiveColumnTable1);

        Table fiveColumnTable2 = new Table(fiveColumnWidth);
        String item = invoice.getItemName();
            fiveColumnTable2.addCell(new Cell().add(new Paragraph(invoice.getPaymentdate())) // Use Paragraph here
                    .setTextAlignment(TextAlignment.CENTER));

            fiveColumnTable2.addCell(new Cell().add(new Paragraph(item)) // Use Paragraph here
                    .setTextAlignment(TextAlignment.CENTER));

            fiveColumnTable2.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getNoOfDays())))
                    .setTextAlignment(TextAlignment.CENTER));

            fiveColumnTable2.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getPricePerDay())))
                    .setTextAlignment(TextAlignment.CENTER));

            fiveColumnTable2.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getTotalPrice())))
                    .setTextAlignment(TextAlignment.CENTER));
        document.add(fiveColumnTable2.setMarginBottom(20f));
        float onetwo[] = {threecol+125f,threecol*2};
        Table totalTable1=new Table(onetwo);
        totalTable1.addCell(new Cell().add(new Paragraph(" "))
                .setBorder(Border.NO_BORDER));
        totalTable1.addCell(divider).setBorder(Border.NO_BORDER);
        //document.add(totalTable1);

        Table totalTable2=new Table(fiveColumnWidth);
//        totalTable2.addCell(new Cell().add(new Paragraph(" "))
//                .setBorder(Border.NO_BORDER).setMarginLeft(10f));
        totalTable2.addCell(new Cell().add(new Paragraph("TDS Percentage"))
                .setBold().setBorder(Border.NO_BORDER));
        totalTable2.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getTds())))
                .setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        Table totalTable3=new Table(fiveColumnWidth);
        totalTable3.addCell(new Cell().add(new Paragraph("TotalAmount"))
                .setBold().setBorder(Border.NO_BORDER));
        totalTable3.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getTotalAmountAfterTDS())))
                .setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

        document.add(onesp);
        //document.add(totalTable1);
        document.add(totalTable2);
        document.add(totalTable3);

        Table amountTable = new Table(twocolWidth);
        amountTable.addCell(new Cell().add(new Paragraph("Amount In Words ")) // Use Paragraph here
                .setBorder(Border.NO_BORDER).setBold());
        amountTable.addCell(new Cell().add(new Paragraph(invoice.getTotalAmountAfterTDSInWords()+" only"))
                .setBorder(Border.NO_BORDER));

        document.add(amountTable);

        Table table2 = new Table(twocolWidth);
        table2.addCell(new Cell().add(new Paragraph(" ")) // Use Paragraph here
                .setBorder(Border.NO_BORDER).setBold());
        Table signtable=new Table(new float[]{twocol/2,twocol/2});
        signtable.addCell(new Cell().add(new Paragraph("sign"))
                .setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(signtable).setBorder(Border.NO_BORDER));
        document.add(table2);
        document.close();
        return invoice;
    }
}
