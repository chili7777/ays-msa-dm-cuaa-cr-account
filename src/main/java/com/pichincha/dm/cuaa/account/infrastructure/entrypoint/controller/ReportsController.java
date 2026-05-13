package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.GetAccountStatementInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final GetAccountStatementInputPort reportUseCase;

    @GetMapping({"/account-statement", "/reportes"})
    public Mono<ResponseEntity<Object>> getAccountStatement(
            @RequestHeader(name = "x-guid") String xGuid,
            @RequestHeader(name = "x-app") String xApp,
            @RequestParam UUID customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String fecha,
            @RequestParam(defaultValue = "json") String format) {

        LocalDate finalStartDate = startDate;
        LocalDate finalEndDate = endDate;

        if (fecha != null && fecha.contains(",")) {
            try {
                String[] parts = fecha.split(",");
                finalStartDate = LocalDate.parse(parts[0]);
                finalEndDate = LocalDate.parse(parts[1]);
            } catch (Exception e) {
                return Mono.error(new IllegalArgumentException("El formato de fecha debe ser YYYY-MM-DD,YYYY-MM-DD"));
            }
        }

        if (finalStartDate == null) finalStartDate = LocalDate.now().minusMonths(1);
        if (finalEndDate == null) finalEndDate = LocalDate.now();

        final LocalDate sDate = finalStartDate;
        final LocalDate eDate = finalEndDate;

        if (format.equalsIgnoreCase("pdf")) {
            return reportUseCase.getReportPdf(customerId, sDate, eDate)
                    .map(pdfBytes -> ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statement.pdf")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(pdfBytes));
        } else if (format.equalsIgnoreCase("base64")) {
            return reportUseCase.getReportPdf(customerId, sDate, eDate)
                    .map(pdfBytes -> {
                        String base64 = java.util.Base64.getEncoder().encodeToString(pdfBytes);
                        return ResponseEntity.ok(java.util.Map.of(
                                "fileName", "statement.pdf",
                                "contentType", "application/pdf",
                                "base64", base64
                        ));
                    });
        } else {
            return reportUseCase.getReport(customerId, sDate, eDate)
                    .map(report -> ResponseEntity.ok((Object) report));
        }
    }
}