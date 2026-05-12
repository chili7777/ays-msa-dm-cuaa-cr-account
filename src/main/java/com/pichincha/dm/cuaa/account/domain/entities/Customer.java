package com.pichincha.dm.cuaa.account.domain.entities;

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

/**
 * Domain record representing a customer within the core business logic.
 * Encapsulates essential person attributes and identity information, serving as the
 * primary model for customer lifecycle operations and validation.
 */
public record Customer(CustomerId id,
                       Identification identification,
                       FullName fullName,
                       Gender gender,
                       Age age,
                       Email email,
                       Phone phone,
                       Address address,
                       Password password,
                       Status status) implements Person {
}