package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountPatchRequestDto;

public final class AccountPatchRequestDtoMother {

    private AccountPatchRequestDtoMother() {
    }

    public static AccountPatchRequestDto random() {
        return create(
                AccountTypeMother.random().getValue(),
                StatusMother.random().getValue()
        );
    }

    public static AccountPatchRequestDto create(String accountType, Boolean status) {
        AccountPatchRequestDto dto = new AccountPatchRequestDto();
        dto.setAccountType(AccountPatchRequestDto.AccountTypeEnum.fromValue(accountType));
        dto.setStatus(status);
        return dto;
    }
}