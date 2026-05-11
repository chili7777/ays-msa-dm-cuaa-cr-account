package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountNumber;

public final class AccountNumberMother {

    // OpenAPI: maxLength 40. Pattern produces 16 chars — well within the limit.
    private static final String ACCOUNT_NUMBER_PATTERN    = "ACC-####-????-##";
    private static final int    MAX_ACCOUNT_NUMBER_LENGTH = 40;

    private AccountNumberMother() {
    }

    public static AccountNumber random() {
        String value = FakerMother.faker().bothify(ACCOUNT_NUMBER_PATTERN).toUpperCase();
        ensureLength(value);
        return create(value);
    }

    public static AccountNumber create(String value) {
        return new AccountNumber(value);
    }

    private static void ensureLength(String value) {
        if (value.length() > MAX_ACCOUNT_NUMBER_LENGTH) {
            throw new IllegalStateException(
                    "Generated account number exceeds OpenAPI max length of " + MAX_ACCOUNT_NUMBER_LENGTH
            );
        }
    }
}