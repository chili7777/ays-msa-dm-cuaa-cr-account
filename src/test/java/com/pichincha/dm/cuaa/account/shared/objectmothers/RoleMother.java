package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Role;

public final class RoleMother {
    public static Role random() {
        return new Role("USER");
    }

    public static Role admin() {
        return new Role("ADMIN");
    }
}