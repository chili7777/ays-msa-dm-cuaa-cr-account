package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountUpdateRequestDto;

public final class JsonMother {

    private JsonMother() {
    }

    public static String fromObject(Object value) {
        if (value instanceof AccountCreateRequestDto accountCreateRequestDto) {
            return fromAccountCreateRequestDto(accountCreateRequestDto);
        }
        if (value instanceof AccountUpdateRequestDto accountUpdateRequestDto) {
            return fromAccountUpdateRequestDto(accountUpdateRequestDto);
        }
        if (value instanceof AccountPatchRequestDto accountPatchRequestDto) {
            return fromAccountPatchRequestDto(accountPatchRequestDto);
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

    private static String fromAccountUpdateRequestDto(AccountUpdateRequestDto dto) {
        return "{" +
                "\"accountType\":\"" + dto.getAccountType().getValue() + "\"," +
                "\"status\":" + dto.getStatus() +
                "}";
    }

    private static String fromAccountPatchRequestDto(AccountPatchRequestDto dto) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        if (dto.getAccountType() != null) {
            json.append("\"accountType\":\"").append(dto.getAccountType().getValue()).append("\"");
            first = false;
        }
        if (dto.getStatus() != null) {
            if (!first) json.append(",");
            json.append("\"status\":").append(dto.getStatus());
        }
        json.append("}");
        return json.toString();
    }
}