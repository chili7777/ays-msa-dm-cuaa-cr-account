package com.pichincha.dm.cuaa.account.domain.entities;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Address;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.FullName;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Phone;

public interface Person {
    FullName fullName();
    Identification identification();
    Address address();
    Phone phone();
}