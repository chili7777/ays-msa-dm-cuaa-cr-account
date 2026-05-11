package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.ClientId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountNumber;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.ValueObject;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountHttpRequestMapper {

    @Mapping(target = "clientId",       source = "clientId",       qualifiedByName = "toClientId")
    @Mapping(target = "accountNumber",  source = "accountNumber",  qualifiedByName = "toAccountNumber")
    @Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "toAccountType")
    @Mapping(target = "initialBalance", source = "initialBalance", qualifiedByName = "toInitialBalance")
    @Mapping(target = "status",         source = "status",         qualifiedByName = "toStatus")
    Account toAccount(AccountCreateRequestDto accountCreateRequestDto);

    @Named("toClientId")
    default ClientId toClientId(java.util.UUID clientIdUuid) {
        return new ClientId(clientIdUuid.toString());
    }

    @Named("toAccountNumber")
    default AccountNumber toAccountNumber(String accountNumber) {
        return new AccountNumber(accountNumber);
    }

    @Named("toAccountType")
    default AccountType toAccountType(AccountCreateRequestDto.AccountTypeEnum accountTypeEnum) {
        return new AccountType(accountTypeEnum.getValue());
    }

    @Named("toInitialBalance")
    default InitialBalance toInitialBalance(Double initialBalance) {
        return new InitialBalance(initialBalance);
    }

    @Named("toStatus")
    default Status toStatus(Boolean status) {
        return new Status(status);
    }

    @Named("fromValueObjectToString")
    default String fromValueObjectToString(ValueObject<String> valueObject) {
        return valueObject.getValue();
    }

    @Named("fromValueObjectToDouble")
    default Double fromValueObjectToDouble(ValueObject<Double> valueObject) {
        return valueObject.getValue();
    }

    @Named("fromValueObjectToBoolean")
    default Boolean fromValueObjectToBoolean(ValueObject<Boolean> valueObject) {
        return valueObject.getValue();
    }
}