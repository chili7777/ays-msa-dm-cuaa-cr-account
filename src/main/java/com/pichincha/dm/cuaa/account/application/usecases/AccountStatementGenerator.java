package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.dto.AccountStatementReport;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.GetAccountStatementInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountStatementGenerator implements GetAccountStatementInputPort {

    private final GetCustomerByIdOutputPort customerPort;
    private final ListAccountsOutputPort accountsPort;
    private final GetMovementsByDateRangeOutputPort movementsPort;
    private final GeneratePdfOutputPort pdfPort;

    @Override
    public Mono<AccountStatementReport> getReport(UUID customerId, LocalDate startDate, LocalDate endDate) {
        CustomerId cid = new CustomerId(customerId.toString());
        
        return customerPort.findById(cid)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found: " + customerId)))
                .flatMap(customer -> accountsPort.findAccountsByCustomerId(cid)
                        .flatMap(account -> movementsPort.findByAccountIdAndDateRange(
                                account.accountId(), 
                                startDate.atStartOfDay(), 
                                endDate.atTime(LocalTime.MAX))
                                .collectList()
                                .map(movements -> AccountStatementReport.AccountStatementItem.builder()
                                        .account(account)
                                        .movements(movements)
                                        .build()))
                        .collectList()
                        .map(items -> {
                            double totalDebits = items.stream()
                                    .flatMap(item -> item.getMovements().stream())
                                    .filter(m -> m.movementType().getValue().equalsIgnoreCase("WITHDRAWAL"))
                                    .mapToDouble(m -> m.amount().getValue())
                                    .sum();

                            double totalCredits = items.stream()
                                    .flatMap(item -> item.getMovements().stream())
                                    .filter(m -> m.movementType().getValue().equalsIgnoreCase("DEPOSIT"))
                                    .mapToDouble(m -> m.amount().getValue())
                                    .sum();

                            return AccountStatementReport.builder()
                                    .client(customer)
                                    .startDate(startDate)
                                    .endDate(endDate)
                                    .accounts(items)
                                    .totalGeneralDebits(totalDebits)
                                    .totalGeneralCredits(totalCredits)
                                    .generationDate(LocalDate.now())
                                    .build();
                        }));
    }

    @Override
    public Mono<byte[]> getReportPdf(UUID customerId, LocalDate startDate, LocalDate endDate) {
        return getReport(customerId, startDate, endDate)
                .flatMap(pdfPort::generateStatementPdf);
    }
}