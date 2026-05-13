package com.pichincha.dm.cuaa.account.application.usecases.dto;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountStatementReport {
    Customer client;
    LocalDate startDate;
    LocalDate endDate;
    List<AccountStatementItem> accounts;
    Double totalGeneralDebits;
    Double totalGeneralCredits;
    LocalDate generationDate;

    @Value
    @Builder
    public static class AccountStatementItem {
        Account account;
        List<Movement> movements;
    }
}