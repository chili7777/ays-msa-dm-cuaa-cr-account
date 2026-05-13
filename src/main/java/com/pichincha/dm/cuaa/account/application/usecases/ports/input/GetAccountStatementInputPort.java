package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.application.usecases.dto.AccountStatementReport;
import java.time.LocalDate;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface GetAccountStatementInputPort {
    Mono<AccountStatementReport> getReport(UUID customerId, LocalDate startDate, LocalDate endDate);
    Mono<byte[]> getReportPdf(UUID customerId, LocalDate startDate, LocalDate endDate);
}