package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.application.usecases.dto.AccountStatementReport;
import reactor.core.publisher.Mono;

public interface GeneratePdfOutputPort {
    Mono<byte[]> generateStatementPdf(AccountStatementReport report);
}