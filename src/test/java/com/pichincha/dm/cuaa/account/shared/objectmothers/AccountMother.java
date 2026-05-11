package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountNumber;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public final class AccountMother {

    private AccountMother() {
    }

    public static Account random() {
        return create(
                AccountIdMother.random(),
                CustomerIdMother.random(),
                AccountNumberMother.random(),
                AccountTypeMother.random(),
                InitialBalanceMother.random(),
                StatusMother.random()
        );
    }

    public static Account randomWithNullId() {
        return create(
                null,
                CustomerIdMother.random(),
                AccountNumberMother.random(),
                AccountTypeMother.random(),
                InitialBalanceMother.random(),
                StatusMother.random()
        );
    }

    public static Account create(AccountId accountId,
                                 CustomerId clientId,
                                 AccountNumber accountNumber,
                                 AccountType accountType,
                                 InitialBalance initialBalance,
                                 Status status) {
        return new Account(accountId, clientId, accountNumber, accountType, initialBalance, status);
    }
}