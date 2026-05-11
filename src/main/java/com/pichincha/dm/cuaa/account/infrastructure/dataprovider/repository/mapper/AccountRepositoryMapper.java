package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountNumber;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.ValueObject;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountRepositoryMapper {

	// Account (domain) → AccountEntity (infrastructure: raw primitives for DB)
	@Mapping(target = "accountId",      source = "accountId",      qualifiedByName = "fromAccountIdToString")
	@Mapping(target = "clientId",       source = "clientId",       qualifiedByName = "fromCustomerIdToString")
	@Mapping(target = "accountNumber",  source = "accountNumber",  qualifiedByName = "fromValueObjectToString")
	@Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "fromValueObjectToString")
	@Mapping(target = "initialBalance", source = "initialBalance", qualifiedByName = "fromValueObjectToDouble")
	@Mapping(target = "status",         source = "status",         qualifiedByName = "fromValueObjectToBoolean")
	AccountEntity toAccountEntity(Account account);

	// AccountEntity (infrastructure: raw primitives) → Account (domain)
	@Mapping(target = "accountId",      source = "accountId",      qualifiedByName = "toAccountId")
	@Mapping(target = "clientId",       source = "clientId",       qualifiedByName = "toCustomerId")
	@Mapping(target = "accountNumber",  source = "accountNumber",  qualifiedByName = "toAccountNumber")
	@Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "toAccountType")
	@Mapping(target = "initialBalance", source = "initialBalance", qualifiedByName = "toInitialBalance")
	@Mapping(target = "status",         source = "status",         qualifiedByName = "toStatus")
	Account toAccount(AccountEntity accountEntity);

	// ---- from value objects (domain → raw primitives) ----

	@Named("fromAccountIdToString")
	default String fromAccountIdToString(AccountId accountId) {
		return accountId == null ? null : accountId.getValue();
	}

	@Named("fromCustomerIdToString")
	default String fromCustomerIdToString(CustomerId clientId) {
		return clientId == null ? null : clientId.getValue();
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

	// ---- to value objects (raw primitives → domain) ----

	@Named("toAccountId")
	default AccountId toAccountId(String accountId) {
		return accountId == null ? null : new AccountId(accountId);
	}

	@Named("toCustomerId")
	default CustomerId toCustomerId(String clientId) {
		return clientId == null ? null : new CustomerId(clientId);
	}

	@Named("toAccountNumber")
	default AccountNumber toAccountNumber(String accountNumber) {
		return accountNumber == null ? null : new AccountNumber(accountNumber);
	}

	@Named("toAccountType")
	default AccountType toAccountType(String accountType) {
		return accountType == null ? null : new AccountType(accountType);
	}

	@Named("toInitialBalance")
	default InitialBalance toInitialBalance(Double initialBalance) {
		return initialBalance == null ? null : new InitialBalance(initialBalance);
	}

	@Named("toStatus")
	default Status toStatus(Boolean status) {
		return status == null ? null : new Status(status);
	}
}