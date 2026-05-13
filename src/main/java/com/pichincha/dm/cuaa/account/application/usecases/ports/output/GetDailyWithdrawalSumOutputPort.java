package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import reactor.core.publisher.Mono;

public interface GetDailyWithdrawalSumOutputPort {
    Mono<Double> getSumByAccountIdAndDate(AccountId accountId);
}