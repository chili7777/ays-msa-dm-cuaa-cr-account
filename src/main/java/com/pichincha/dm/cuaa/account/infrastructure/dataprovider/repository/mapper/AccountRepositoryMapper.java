package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountRepositoryMapper {

	AccountEntity toAccountEntity(Account account);

	Account toAccount(AccountEntity accountEntity);
}