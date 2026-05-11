package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Address;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Email;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.FullName;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Phone;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public final class CustomerMother {
    public static Customer random() {
        return new Customer(
                CustomerIdMother.random(),
                IdentificationMother.random(),
                FullNameMother.random(),
                EmailMother.random(),
                PhoneMother.random(),
                AddressMother.random(),
                StatusMother.random()
        );
    }

    public static Customer withId(CustomerId id) {
        return new Customer(
                id,
                IdentificationMother.random(),
                FullNameMother.random(),
                EmailMother.random(),
                PhoneMother.random(),
                AddressMother.random(),
                StatusMother.random()
        );
    }
}