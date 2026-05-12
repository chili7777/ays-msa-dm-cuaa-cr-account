package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Address;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Age;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Email;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.FullName;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Gender;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Phone;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public final class CustomerMother {
    public static Customer random() {
        return new Customer(
                CustomerIdMother.random(),
                IdentificationMother.random(),
                FullNameMother.random(),
                GenderMother.random(),
                AgeMother.random(),
                EmailMother.random(),
                PhoneMother.random(),
                AddressMother.random(),
                PasswordMother.random(),
                StatusMother.random()
        );
    }

    public static Customer withId(CustomerId id) {
        return new Customer(
                id,
                IdentificationMother.random(),
                FullNameMother.random(),
                GenderMother.random(),
                AgeMother.random(),
                EmailMother.random(),
                PhoneMother.random(),
                AddressMother.random(),
                PasswordMother.random(),
                StatusMother.random()
        );
    }

    public static Customer withIdAndCredentials(CustomerId id, String identification, String password) {
        return new Customer(
                id,
                new Identification(identification),
                FullNameMother.random(),
                GenderMother.random(),
                AgeMother.random(),
                EmailMother.random(),
                PhoneMother.random(),
                AddressMother.random(),
                new Password(password),
                StatusMother.random()
        );
    }
}