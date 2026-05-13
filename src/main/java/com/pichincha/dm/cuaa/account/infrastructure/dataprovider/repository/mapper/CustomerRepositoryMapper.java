package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper;

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
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerRepositoryMapper {

    @Mapping(target = "id", source = "id", qualifiedByName = "fromCustomerIdToString")
    @Mapping(target = "identification", source = "identification", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "fullName", source = "fullName", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "age", source = "age", qualifiedByName = "fromValueObjectToInteger")
    @Mapping(target = "email", source = "email", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "address", source = "address", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "password", source = "password", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "fromValueObjectToBoolean")
    @Mapping(target = "role", source = "role", qualifiedByName = "fromValueObjectToString")
    ClientEntity toCustomerEntity(Customer customer);

    @Mapping(target = "id", source = "id", qualifiedByName = "toCustomerId")
    @Mapping(target = "identification", source = "identification", qualifiedByName = "toIdentification")
    @Mapping(target = "fullName", source = "fullName", qualifiedByName = "toFullName")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "toGender")
    @Mapping(target = "age", source = "age", qualifiedByName = "toAge")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "address", source = "address", qualifiedByName = "toAddress")
    @Mapping(target = "password", source = "password", qualifiedByName = "toPassword")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    @Mapping(target = "role", source = "role", qualifiedByName = "toRole")
    Customer toCustomer(ClientEntity customerEntity);

    @Named("fromCustomerIdToString")
    default String fromCustomerIdToString(CustomerId customerId) {
        return customerId == null ? null : customerId.getValue();
    }

    @Named("fromValueObjectToString")
    default String fromValueObjectToString(ValueObject<String> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
    }

    @Named("fromValueObjectToBoolean")
    default Boolean fromValueObjectToBoolean(ValueObject<Boolean> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
    }

    @Named("fromValueObjectToInteger")
    default Integer fromValueObjectToInteger(ValueObject<Integer> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
    }

    @Named("toCustomerId")
    default CustomerId toCustomerId(String id) {
        return id == null ? null : new CustomerId(id);
    }

    @Named("toIdentification")
    default Identification toIdentification(String identification) {
        return identification == null ? null : new Identification(identification);
    }

    @Named("toGender")
    default Gender toGender(String gender) {
        return gender == null ? null : new Gender(gender);
    }

    @Named("toAge")
    default Age toAge(Integer age) {
        return age == null ? null : new Age(age);
    }

    @Named("toFullName")
    default FullName toFullName(String fullName) {
        return fullName == null ? null : new FullName(fullName);
    }

    @Named("toEmail")
    default Email toEmail(String email) {
        return email == null ? null : new Email(email);
    }

    @Named("toPhone")
    default Phone toPhone(String phone) {
        return phone == null ? null : new Phone(phone);
    }

    @Named("toAddress")
    default Address toAddress(String address) {
        return address == null ? null : new Address(address);
    }

    @Named("toPassword")
    default Password toPassword(String password) {
        return password == null ? null : new Password(password);
    }

    @Named("toStatus")
    default Status toStatus(Boolean status) {
        return status == null ? null : new Status(status);
    }

    @Named("toRole")
    default Role toRole(String role) {
        return role == null ? null : new Role(role);
    }
}