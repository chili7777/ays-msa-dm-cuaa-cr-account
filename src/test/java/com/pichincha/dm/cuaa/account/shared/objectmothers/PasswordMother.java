package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;

public final class PasswordMother {
    public static Password random() {
        return new Password(FakerMother.random().internet().password(8, 20));
    }
}