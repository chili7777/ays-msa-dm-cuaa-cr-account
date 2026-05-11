package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Address;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Email;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.FullName;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Phone;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.ValueObject;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerUpdateRequestDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerHttpRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identification", source = "identification", qualifiedByName = "toIdentification")
    @Mapping(target = "fullName", source = "name", qualifiedByName = "toFullName")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddress")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    Customer toCustomer(CustomerCreateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identification", source = "identification", qualifiedByName = "toIdentification")
    @Mapping(target = "fullName", source = "name", qualifiedByName = "toFullName")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddress")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    Customer toCustomer(CustomerUpdateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identification", source = "identification", qualifiedByName = "toIdentification")
    @Mapping(target = "fullName", source = "name", qualifiedByName = "toFullName")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddress")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    Customer toCustomer(CustomerPatchRequestDto dto);

    @Mapping(target = "customerId", source = "id", qualifiedByName = "fromId")
    @Mapping(target = "name", source = "fullName", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "identification", source = "identification", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "address", source = "address", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "email", source = "email", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "fromValueObjectToBoolean")
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "password", ignore = true)
    CustomerDto toCustomerDto(Customer customer);

    @Named("toIdentification")
    default Identification toIdentification(String value) {
        return value == null ? null : new Identification(value);
    }

    @Named("toFullName")
    default FullName toFullName(String value) {
        return value == null ? null : new FullName(value);
    }

    @Named("toEmail")
    default Email toEmail(String value) {
        return value == null ? null : new Email(value);
    }

    @Named("toPhone")
    default Phone toPhone(String value) {
        return value == null ? null : new Phone(value);
    }

    @Named("toAddress")
    default Address toAddress(String value) {
        return value == null ? null : new Address(value);
    }

    @Named("toStatus")
    default Status toStatus(Boolean value) {
        return value == null ? null : new Status(value);
    }

    @Named("fromId")
    default UUID fromId(CustomerId id) {
        return id == null ? null : UUID.fromString(id.getValue());
    }

    @Named("fromValueObjectToString")
    default String fromValueObjectToString(ValueObject<String> vo) {
        return vo == null ? null : vo.getValue();
    }

    @Named("fromValueObjectToBoolean")
    default Boolean fromValueObjectToBoolean(ValueObject<Boolean> vo) {
        return vo == null ? null : vo.getValue();
    }
}