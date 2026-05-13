package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pichincha.dm.cuaa.account.application.usecases.dto.AccountStatementReport;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GeneratePdfOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class PdfStatementAdapter implements GeneratePdfOutputPort {

    @Override
    public Mono<byte[]> generateStatementPdf(AccountStatementReport report) {
        return Mono.fromCallable(() -> {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Title
            Paragraph title = new Paragraph("Estado de Cuenta", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Client Info
            document.add(new Paragraph("Cliente: " + report.getClient().fullName().getValue(), headerFont));
            document.add(new Paragraph("Identificación: " + report.getClient().identification().getValue(), normalFont));
            document.add(new Paragraph("Periodo: " + report.getStartDate() + " al " + report.getEndDate(), normalFont));
            document.add(new Paragraph("Fecha de generación: " + report.getGenerationDate(), normalFont));
            document.add(new Paragraph(" "));

            // Accounts and Movements
            for (AccountStatementReport.AccountStatementItem item : report.getAccounts()) {
                document.add(new Paragraph("Cuenta: " + item.getAccount().accountNumber().getValue() + 
                        " (" + item.getAccount().accountType().getValue() + ")", headerFont));
                document.add(new Paragraph("Saldo inicial: $" + item.getAccount().initialBalance().getValue(), normalFont));
                document.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.addCell(new PdfPCell(new Phrase("Fecha", headerFont)));
                table.addCell(new PdfPCell(new Phrase("Tipo", headerFont)));
                table.addCell(new PdfPCell(new Phrase("Monto", headerFont)));
                table.addCell(new PdfPCell(new Phrase("Descripción", headerFont)));

                for (Movement m : item.getMovements()) {
                    table.addCell(new Phrase(m.movementDate().getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), normalFont));
                    table.addCell(new Phrase(m.movementType().getValue(), normalFont));
                    table.addCell(new Phrase("$" + m.amount().getValue(), normalFont));
                    table.addCell(new Phrase(m.description() != null ? m.description().getValue() : "", normalFont));
                }
                document.add(table);
                document.add(new Paragraph(" "));
            }

            // Totals
            document.add(new Paragraph("RESUMEN GENERAL", headerFont));
            document.add(new Paragraph("Total Débitos: $" + report.getTotalGeneralDebits(), normalFont));
            document.add(new Paragraph("Total Créditos: $" + report.getTotalGeneralCredits(), normalFont));

            document.close();
            return out.toByteArray();
        });
    }
}