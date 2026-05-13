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

    @GetMapping("/account-statement")
    public Mono<ResponseEntity<Object>> getAccountStatement(
            @RequestHeader(name = "x-guid") String xGuid,
            @RequestHeader(name = "x-app") String xApp,
            @RequestParam UUID customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "json") String format) {

        if (format.equalsIgnoreCase("pdf")) {
            return reportUseCase.getReportPdf(customerId, startDate, endDate)
                    .map(pdfBytes -> ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statement.pdf")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(pdfBytes));
        } else {
            return reportUseCase.getReport(customerId, startDate, endDate)
                    .map(report -> ResponseEntity.ok((Object) report));
        }
    }
}