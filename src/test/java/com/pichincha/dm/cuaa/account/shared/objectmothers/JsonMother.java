package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountUpdateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerUpdateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementUpdateRequestDto;

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
        if (value instanceof CustomerCreateRequestDto customerCreateRequestDto) {
            return fromCustomerCreateRequestDto(customerCreateRequestDto);
        }
        if (value instanceof CustomerUpdateRequestDto customerUpdateRequestDto) {
            return fromCustomerUpdateRequestDto(customerUpdateRequestDto);
        }
        if (value instanceof CustomerPatchRequestDto customerPatchRequestDto) {
            return fromCustomerPatchRequestDto(customerPatchRequestDto);
        }
        if (value instanceof MovementCreateRequestDto movementCreateRequestDto) {
            return fromMovementCreateRequestDto(movementCreateRequestDto);
        }
        if (value instanceof MovementUpdateRequestDto movementUpdateRequestDto) {
            return fromMovementUpdateRequestDto(movementUpdateRequestDto);
        }
        if (value instanceof MovementPatchRequestDto movementPatchRequestDto) {
            return fromMovementPatchRequestDto(movementPatchRequestDto);
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

    private static String fromCustomerCreateRequestDto(CustomerCreateRequestDto dto) {
        return "{" +
                "\"name\":\"" + dto.getName() + "\"," +
                "\"identification\":\"" + dto.getIdentification() + "\"," +
                "\"email\":\"" + dto.getEmail() + "\"," +
                "\"phone\":\"" + dto.getPhone() + "\"," +
                "\"address\":\"" + dto.getAddress() + "\"," +
                "\"status\":" + dto.getStatus() + "," +
                "\"password\":\"" + dto.getPassword() + "\"," +
                "\"gender\":\"" + (dto.getGender() != null ? dto.getGender().getValue() : "OTHER") + "\"," +
                "\"age\":" + (dto.getAge() != null ? dto.getAge() : 30) +
                "}";
    }

    private static String fromCustomerUpdateRequestDto(CustomerUpdateRequestDto dto) {
        return "{" +
                "\"name\":\"" + dto.getName() + "\"," +
                "\"identification\":\"" + dto.getIdentification() + "\"," +
                "\"email\":\"" + dto.getEmail() + "\"," +
                "\"phone\":\"" + dto.getPhone() + "\"," +
                "\"address\":\"" + dto.getAddress() + "\"," +
                "\"status\":" + dto.getStatus() + "," +
                "\"password\":\"" + dto.getPassword() + "\"," +
                "\"gender\":\"" + (dto.getGender() != null ? dto.getGender().getValue() : "OTHER") + "\"," +
                "\"age\":" + (dto.getAge() != null ? dto.getAge() : 30) +
                "}";
    }

    private static String fromCustomerPatchRequestDto(CustomerPatchRequestDto dto) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        if (dto.getName() != null) {
            json.append("\"name\":\"").append(dto.getName()).append("\"");
            first = false;
        }
        if (dto.getPhone() != null) {
            if (!first) json.append(",");
            json.append("\"phone\":\"").append(dto.getPhone()).append("\"");
            first = false;
        }
        if (dto.getAddress() != null) {
            if (!first) json.append(",");
            json.append("\"address\":\"").append(dto.getAddress()).append("\"");
            first = false;
        }
        if (dto.getStatus() != null) {
            if (!first) json.append(",");
            json.append("\"status\":").append(dto.getStatus());
        }
        json.append("}");
        return json.toString();
    }

    private static String fromMovementCreateRequestDto(MovementCreateRequestDto dto) {
        return "{" +
                "\"accountId\":\"" + dto.getAccountId() + "\"," +
                "\"movementDate\":\"" + dto.getMovementDate() + "\"," +
                "\"movementType\":\"" + dto.getMovementType().getValue() + "\"," +
                "\"amount\":" + dto.getAmount() +
                "}";
    }

    private static String fromMovementUpdateRequestDto(MovementUpdateRequestDto dto) {
        return "{" +
                "\"movementType\":\"" + dto.getMovementType().getValue() + "\"," +
                "\"amount\":" + dto.getAmount() +
                "}";
    }

    private static String fromMovementPatchRequestDto(MovementPatchRequestDto dto) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        if (dto.getMovementType() != null) {
            json.append("\"movementType\":\"").append(dto.getMovementType().getValue()).append("\"");
            first = false;
        }
        if (dto.getAmount() != null) {
            if (!first) json.append(",");
            json.append("\"amount\":").append(dto.getAmount());
        }
        json.append("}");
        return json.toString();
    }
}