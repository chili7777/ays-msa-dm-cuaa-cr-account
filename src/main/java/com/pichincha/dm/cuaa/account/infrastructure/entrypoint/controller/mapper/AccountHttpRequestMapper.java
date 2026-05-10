package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
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

    @Mapping(
            target = "accountType",
            source = "accountType",
            qualifiedByName = "fromAccountTypeEnumToString"
    )
    Account toAccount(AccountCreateRequestDto accountCreateRequestDto);

    @Named("fromAccountTypeEnumToString")
    default String fromAccountTypeEnumToString(AccountCreateRequestDto.AccountTypeEnum accountTypeEnum) {
        return accountTypeEnum == null ? null : accountTypeEnum.getValue();
    }
}