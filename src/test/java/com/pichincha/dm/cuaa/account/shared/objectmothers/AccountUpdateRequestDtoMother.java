package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountUpdateRequestDto;

public final class AccountUpdateRequestDtoMother {

    private AccountUpdateRequestDtoMother() {
    }

    public static AccountUpdateRequestDto random() {
        return create(
                AccountTypeMother.random().getValue(),
                StatusMother.random().getValue()
        );
    }

    public static AccountUpdateRequestDto create(String accountType, Boolean status) {
        AccountUpdateRequestDto dto = new AccountUpdateRequestDto();
        dto.setAccountType(AccountUpdateRequestDto.AccountTypeEnum.fromValue(accountType));
        dto.setStatus(status);
        return dto;
    }
}