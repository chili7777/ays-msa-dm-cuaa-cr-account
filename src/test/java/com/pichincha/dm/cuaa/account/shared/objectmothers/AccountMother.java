package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.ClientId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountNumber;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public final class AccountMother {

    private AccountMother() {
    }

    public static Account random() {
        return create(
                ClientIdMother.random(),
                AccountNumberMother.random(),
                AccountTypeMother.random(),
                InitialBalanceMother.random(),
                StatusMother.random()
        );
    }

    public static Account create(ClientId clientId,
                                 AccountNumber accountNumber,
                                 AccountType accountType,
                                 InitialBalance initialBalance,
                                 Status status) {
        return new Account(clientId, accountNumber, accountType, initialBalance, status);
    }
}