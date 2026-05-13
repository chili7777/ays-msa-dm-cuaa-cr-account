package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper;

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
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Role;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.ValueObject;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerUpdateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.LoginResponseDto;
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
    @Mapping(target = "gender", source = "gender", qualifiedByName = "toGender")
    @Mapping(target = "age", source = "age", qualifiedByName = "toAge")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddress")
    @Mapping(target = "password", source = "password", qualifiedByName = "toPassword")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    @Mapping(target = "role", source = "role", qualifiedByName = "toRole")
    Customer toCustomer(CustomerCreateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identification", source = "identification", qualifiedByName = "toIdentification")
    @Mapping(target = "fullName", source = "name", qualifiedByName = "toFullName")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "toGender")
    @Mapping(target = "age", source = "age", qualifiedByName = "toAge")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddress")
    @Mapping(target = "password", source = "password", qualifiedByName = "toPassword")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    @Mapping(target = "role", source = "role", qualifiedByName = "toRole")
    Customer toCustomer(CustomerUpdateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identification", source = "identification", qualifiedByName = "toIdentification")
    @Mapping(target = "fullName", source = "name", qualifiedByName = "toFullName")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "toGender")
    @Mapping(target = "age", source = "age", qualifiedByName = "toAge")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddress")
    @Mapping(target = "password", source = "password", qualifiedByName = "toPassword")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    @Mapping(target = "role", source = "role", qualifiedByName = "toRole")
    Customer toCustomer(CustomerPatchRequestDto dto);

    @Mapping(target = "customerId", source = "id", qualifiedByName = "fromId")
    @Mapping(target = "name", source = "fullName", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "identification", source = "identification", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "age", source = "age", qualifiedByName = "fromValueObjectToInteger")
    @Mapping(target = "address", source = "address", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "email", source = "email", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "fromValueObjectToBoolean")
    @Mapping(target = "role", source = "role", qualifiedByName = "fromValueObjectToString")
    CustomerDto toCustomerDto(Customer customer);

    @Mapping(target = "customerId", source = "id", qualifiedByName = "fromId")
    @Mapping(target = "fullName", source = "fullName", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "identification", source = "identification", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "role", source = "role", qualifiedByName = "fromValueObjectToString")
    LoginResponseDto toLoginResponseDto(Customer customer);

    @Named("toIdentification")
    default Identification toIdentification(String value) {
        return value == null ? null : new Identification(value);
    }

    @Named("toFullName")
    default FullName toFullName(String value) {
        return value == null ? null : new FullName(value);
    }

    @Named("toGender")
    default Gender toGender(String value) {
        return value == null ? null : new Gender(value);
    }

    @Named("toAge")
    default Age toAge(Integer value) {
        return value == null ? null : new Age(value);
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

    @Named("toPassword")
    default Password toPassword(String value) {
        return value == null ? null : new Password(value);
    }

    @Named("toStatus")
    default Status toStatus(Boolean value) {
        return value == null ? null : new Status(value);
    }

    @Named("toRole")
    default Role toRole(String value) {
        return value == null ? null : new Role(value);
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

    @Named("fromValueObjectToInteger")
    default Integer fromValueObjectToInteger(ValueObject<Integer> vo) {
        return vo == null ? null : vo.getValue();
    }
}