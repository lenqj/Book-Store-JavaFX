package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.book.BookInterface;

public class PDF {

    public void generatePdf(final String fileName, User user, Map<Long, Map.Entry<BookInterface, Date>> map) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        Paragraph title = new Paragraph("Sold Books by: " + user.getUsername());

        document.add(title);

        PdfPTable t = new PdfPTable(8);
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);

        t.addCell(new PdfPCell(new Phrase("ID")));
        t.addCell(new PdfPCell(new Phrase("Book ID")));
        t.addCell(new PdfPCell(new Phrase("Book Author")));
        t.addCell(new PdfPCell(new Phrase("Book Title")));
        t.addCell(new PdfPCell(new Phrase("Book Published Date")));
        t.addCell(new PdfPCell(new Phrase("Book Stock")));
        t.addCell(new PdfPCell(new Phrase("Book Price")));
        t.addCell(new PdfPCell(new Phrase("Date")));

        for(Map.Entry<Long, Map.Entry<BookInterface, Date>> entry : map.entrySet()) {
            BookInterface book = entry.getValue().getKey();
            t.addCell(String.valueOf(entry.getKey()));
            t.addCell(String.valueOf(book.getId()));
            t.addCell(book.getAuthor());
            t.addCell(book.getTitle());
            t.addCell(String.valueOf(book.getPublishedDate()));
            t.addCell(String.valueOf(book.getStock()));
            t.addCell(String.valueOf(book.getPrice()));
            t.addCell(String.valueOf(entry.getValue().getValue()));
        }

        document.add(t);

        document.close();
        pdfWriter.close();
    }
}