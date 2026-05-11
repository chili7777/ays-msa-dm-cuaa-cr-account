package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public final class StatusMother {

    private StatusMother() {
    }

    public static Status random() {
        return create(FakerMother.faker().bool().bool());
    }

    public static Status create(Boolean value) {
        return new Status(value);
    }
}