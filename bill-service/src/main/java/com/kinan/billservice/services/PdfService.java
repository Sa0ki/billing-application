package com.kinan.billservice.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.kinan.billservice.models.Customer;
import com.kinan.billservice.models.Order;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Eren
 **/
public class PdfService {
    private static final Integer COLUMNS_NUMBER = 4;
    public static void customerInformation(Document document, Customer customer) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);

        Paragraph title = new Paragraph("Facture pour toutes les commandes passées.", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 14, com.itextpdf.text.Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(title);

        Paragraph name = new Paragraph("Client: " + customer.getFirstName() + " " + customer.getLastName(), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12));
        name.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(name);

        Paragraph date = new Paragraph("Date: " + LocalDate.now().toString(), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12));
        date.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(date);

        paragraph.add(new Paragraph("\n\n"));

        document.add(paragraph);
    }
    public static void headerProductsTable(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(PdfService.COLUMNS_NUMBER);
        table.setWidthPercentage(100);

        Stream.of("Order ID", "Date", "Products", "Price").forEach(title -> {
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            cell.setBorderWidth(1);
            cell.setPaddingBottom(5);

            cell.setPhrase(new Phrase(title));

            table.addCell(cell);
        });

        document.add(table);
    }
    public static void bodyProductsTable(Document document, Order order) {
            PdfPTable table = new PdfPTable(PdfService.COLUMNS_NUMBER);
            PdfPTable totalOrderTable = new PdfPTable(1);

            table.setWidthPercentage(100);
            totalOrderTable.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell();
            cell.setPaddingTop(20);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            cell.setBorderWidth(1);
            cell.setPaddingBottom(5);

            cell.setPhrase(new Phrase(order.getId()));
            table.addCell(cell);

            cell.setPhrase(new Phrase(order.getDate().toString()));
            table.addCell(cell);

            Phrase productsNames = new Phrase();
            Phrase productsPrices = new Phrase();

            order.getProducts().forEach((idProduct, product) -> {
                productsNames.add(product.getName());
                productsNames.add("\n");
                productsNames.add("x" + product.getQuantity().toString());
                productsNames.add("\n\n");

                productsPrices.add(Double.toString(product.getPrice() * product.getQuantity()) + "$");
                productsPrices.add("\n\n\n");

            cell.setPhrase(productsNames);
            table.addCell(cell);

            cell.setPhrase(productsPrices);
            table.addCell(cell);

            cell.setPaddingTop(0);
        });

        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPhrase(new Phrase("Order's total", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK)));
        totalOrderTable.addCell(cell);

        cell.setPhrase(new Phrase(order.getTotalDue().toString() + "$"));
        totalOrderTable.addCell(cell);

        try {
            document.add(table);
            document.add(totalOrderTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
