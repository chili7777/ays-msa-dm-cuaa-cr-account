package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;

public final class AccountCreateRequestDtoMother {

    private static final String ACCOUNT_NUMBER_PATTERN = "ACC-####-????-##";
    private static final long   MIN_INITIAL_BALANCE    = 0;
    private static final long   MAX_INITIAL_BALANCE    = 100_000;
    private static final int    INITIAL_BALANCE_DECIMALS = 2;

    private AccountCreateRequestDtoMother() {
    }

    public static AccountCreateRequestDto random() {
        return create(
                UuidMother.random(),
                FakerMother.faker().bothify(ACCOUNT_NUMBER_PATTERN).toUpperCase(),
                FakerMother.faker().options().option(AccountCreateRequestDto.AccountTypeEnum.values()),
                FakerMother.faker().number().randomDouble(INITIAL_BALANCE_DECIMALS, MIN_INITIAL_BALANCE, MAX_INITIAL_BALANCE),
                FakerMother.faker().bool().bool()
        );
    }

    public static AccountCreateRequestDto create(java.util.UUID clientId,
                                                  String accountNumber,
                                                  AccountCreateRequestDto.AccountTypeEnum accountType,
                                                  Double initialBalance,
                                                  Boolean status) {
        return new AccountCreateRequestDto(clientId, accountNumber, accountType, initialBalance, status);
    }
}