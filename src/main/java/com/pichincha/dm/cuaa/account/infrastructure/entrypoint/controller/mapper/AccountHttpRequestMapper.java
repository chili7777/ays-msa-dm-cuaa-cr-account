package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountNumber;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.ValueObject;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountHttpRequestMapper {

    @Mapping(target = "accountId",      source = "accountId",      qualifiedByName = "toAccountId")
    @Mapping(target = "clientId",       source = "clientId",       qualifiedByName = "toCustomerId")
    @Mapping(target = "accountNumber",  source = "accountNumber",  qualifiedByName = "toAccountNumber")
    @Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "toAccountType")
    @Mapping(target = "initialBalance", source = "initialBalance", qualifiedByName = "toInitialBalance")
    @Mapping(target = "status",         source = "status",         qualifiedByName = "toStatus")
    Account toAccount(AccountCreateRequestDto accountCreateRequestDto);

    @Named("toAccountId")
    default AccountId toAccountId(java.util.UUID accountId) {
        return accountId == null ? null : new AccountId(accountId.toString());
    }

    @Named("toCustomerId")
    default CustomerId toCustomerId(java.util.UUID clientIdUuid) {
        return clientIdUuid == null ? null : new CustomerId(clientIdUuid.toString());
    }

    @Named("toAccountNumber")
    default AccountNumber toAccountNumber(String accountNumber) {
        return accountNumber == null ? null : new AccountNumber(accountNumber);
    }

    @Named("toAccountType")
    default AccountType toAccountType(AccountCreateRequestDto.AccountTypeEnum accountTypeEnum) {
        return accountTypeEnum == null ? null : new AccountType(accountTypeEnum.getValue());
    }

    @Named("toInitialBalance")
    default InitialBalance toInitialBalance(Double initialBalance) {
        return initialBalance == null ? null : new InitialBalance(initialBalance);
    }

    @Named("toStatus")
    default Status toStatus(Boolean status) {
        return status == null ? null : new Status(status);
    }

    @Mapping(target = "accountId",      ignore = true)
    @Mapping(target = "clientId",       ignore = true)
    @Mapping(target = "accountNumber",  ignore = true)
    @Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "toAccountTypeFromUpdate")
    @Mapping(target = "initialBalance", ignore = true)
    @Mapping(target = "status",         source = "status",         qualifiedByName = "toStatus")
    Account toAccount(AccountUpdateRequestDto accountUpdateRequestDto);

    @Named("toAccountTypeFromUpdate")
    default AccountType toAccountTypeFromUpdate(AccountUpdateRequestDto.AccountTypeEnum accountTypeEnum) {
        return accountTypeEnum == null ? null : new AccountType(accountTypeEnum.getValue());
    }

    @Mapping(target = "accountId",      ignore = true)
    @Mapping(target = "clientId",       ignore = true)
    @Mapping(target = "accountNumber",  ignore = true)
    @Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "toAccountTypeFromPatch")
    @Mapping(target = "initialBalance", ignore = true)
    @Mapping(target = "status",         source = "status",         qualifiedByName = "toStatus")
    Account toAccount(AccountPatchRequestDto accountPatchRequestDto);

    @Named("toAccountTypeFromPatch")
    default AccountType toAccountTypeFromPatch(AccountPatchRequestDto.AccountTypeEnum accountTypeEnum) {
        return accountTypeEnum == null ? null : new AccountType(accountTypeEnum.getValue());
    }

    @Mapping(target = "accountId",      source = "accountId",      qualifiedByName = "fromAccountIdToUuid")
    @Mapping(target = "clientId",       source = "clientId",       qualifiedByName = "fromCustomerIdToUuid")
    @Mapping(target = "accountNumber",  source = "accountNumber",  qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "fromAccountTypeToEnum")
    @Mapping(target = "initialBalance", source = "initialBalance", qualifiedByName = "fromValueObjectToDouble")
    @Mapping(target = "status",         source = "status",         qualifiedByName = "fromValueObjectToBoolean")
    AccountDto toAccountDto(Account account);

    @Named("fromAccountIdToUuid")
    default java.util.UUID fromAccountIdToUuid(AccountId accountId) {
        return accountId == null ? null : java.util.UUID.fromString(accountId.getValue());
    }

    @Named("fromCustomerIdToUuid")
    default java.util.UUID fromCustomerIdToUuid(CustomerId clientId) {
        return clientId == null ? null : java.util.UUID.fromString(clientId.getValue());
    }

    @Named("fromAccountTypeToEnum")
    default AccountDto.AccountTypeEnum fromAccountTypeToEnum(AccountType accountType) {
        return accountType == null ? null : AccountDto.AccountTypeEnum.fromValue(accountType.getValue());
    }

    @Named("fromValueObjectToString")
    default String fromValueObjectToString(ValueObject<String> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
    }

    @Named("fromValueObjectToDouble")
    default Double fromValueObjectToDouble(ValueObject<Double> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
    }

    @Named("fromValueObjectToBoolean")
    default Boolean fromValueObjectToBoolean(ValueObject<Boolean> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
    }
}