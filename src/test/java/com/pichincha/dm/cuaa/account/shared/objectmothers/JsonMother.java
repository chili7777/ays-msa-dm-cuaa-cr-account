package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;

public final class JsonMother {

    private JsonMother() {
    }

    public static String fromObject(Object value) {
        if (value instanceof AccountCreateRequestDto accountCreateRequestDto) {
            return fromAccountCreateRequestDto(accountCreateRequestDto);
        }
        throw new IllegalArgumentException("Unsupported object type for JsonMother: " + value.getClass().getName());
    }

    private static String fromAccountCreateRequestDto(AccountCreateRequestDto dto) {
        return "{" +
                "\"clientId\":\"" + dto.getClientId() + "\"," +
                "\"accountNumber\":\"" + dto.getAccountNumber() + "\"," +
                "\"accountType\":\"" + dto.getAccountType().getValue() + "\"," +
                "\"initialBalance\":" + dto.getInitialBalance() + "," +
                "\"status\":" + dto.getStatus() +
                "}";
    }
}