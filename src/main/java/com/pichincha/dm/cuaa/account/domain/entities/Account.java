package com.pichincha.dm.cuaa.account.domain.entities;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountNumber;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public record Account(AccountId accountId,
                      CustomerId clientId,
                      AccountNumber accountNumber,
                      AccountType accountType,
                      InitialBalance initialBalance,
                      Status status) {
}