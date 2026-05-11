package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Email;

public final class EmailMother {
    public static Email random() {
        return new Email(FakerMother.random().internet().emailAddress());
    }
}