package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.ClientId;
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
	@Mapping(target = "clientId",       source = "clientId",       qualifiedByName = "fromClientIdToString")
	@Mapping(target = "accountNumber",  source = "accountNumber",  qualifiedByName = "fromValueObjectToString")
	@Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "fromValueObjectToString")
	@Mapping(target = "initialBalance", source = "initialBalance", qualifiedByName = "fromValueObjectToDouble")
	@Mapping(target = "status",         source = "status",         qualifiedByName = "fromValueObjectToBoolean")
	AccountEntity toAccountEntity(Account account);

	// AccountEntity (infrastructure: raw primitives) → Account (domain)
	@Mapping(target = "clientId",       source = "clientId",       qualifiedByName = "toClientId")
	@Mapping(target = "accountNumber",  source = "accountNumber",  qualifiedByName = "toAccountNumber")
	@Mapping(target = "accountType",    source = "accountType",    qualifiedByName = "toAccountType")
	@Mapping(target = "initialBalance", source = "initialBalance", qualifiedByName = "toInitialBalance")
	@Mapping(target = "status",         source = "status",         qualifiedByName = "toStatus")
	Account toAccount(AccountEntity accountEntity);

	// ---- from value objects (domain → raw primitives) ----

	@Named("fromClientIdToString")
	default String fromClientIdToString(ClientId clientId) {
		return clientId.getValue();
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

	// ---- to value objects (raw primitives → domain) ----

	@Named("toClientId")
	default ClientId toClientId(String clientId) {
		return new ClientId(clientId);
	}

	@Named("toAccountNumber")
	default AccountNumber toAccountNumber(String accountNumber) {
		return new AccountNumber(accountNumber);
	}

	@Named("toAccountType")
	default AccountType toAccountType(String accountType) {
		return new AccountType(accountType);
	}

	@Named("toInitialBalance")
	default InitialBalance toInitialBalance(Double initialBalance) {
		return new InitialBalance(initialBalance);
	}

	@Named("toStatus")
	default Status toStatus(Boolean status) {
		return new Status(status);
	}
}