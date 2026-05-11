package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.ClientId;

public final class ClientIdMother {

    private ClientIdMother() {
    }

    public static ClientId random() {
        return create(UuidMother.randomAsString());
    }

    public static ClientId create(String value) {
        return new ClientId(value);
    }
}