package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Amount;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Balance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementDate;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.ValueObject;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementUpdateRequestDto;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MovementHttpRequestMapper {

    @Mapping(target = "movementId", ignore = true)
    @Mapping(target = "accountId", source = "accountId", qualifiedByName = "toAccountId")
    @Mapping(target = "movementDate", source = "movementDate", qualifiedByName = "toMovementDate")
    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "toMovementType")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "toAmount")
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "status", ignore = true)
    Movement toMovement(MovementCreateRequestDto dto);

    @Mapping(target = "movementId", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "movementDate", source = "movementDate", qualifiedByName = "toMovementDate")
    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "toMovementType")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "toAmount")
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "status", ignore = true)
    Movement toMovement(MovementUpdateRequestDto dto);

    @Mapping(target = "movementId", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "movementDate", source = "movementDate", qualifiedByName = "toMovementDate")
    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "toMovementType")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "toAmount")
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "status", ignore = true)
    Movement toMovement(MovementPatchRequestDto dto);

    @Mapping(target = "movementId", source = "movementId", qualifiedByName = "fromMovementId")
    @Mapping(target = "accountId", source = "accountId", qualifiedByName = "fromAccountId")
    @Mapping(target = "movementDate", source = "movementDate", qualifiedByName = "fromMovementDate")
    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "fromMovementType")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "fromAmount")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "fromBalance")
    @Mapping(target = "status", source = "status", qualifiedByName = "fromStatus")
    MovementDto toMovementDto(Movement movement);

    @Named("toAccountId")
    default AccountId toAccountId(UUID value) {
        return value == null ? null : new AccountId(value.toString());
    }

    @Named("toMovementDate")
    default MovementDate toMovementDate(OffsetDateTime value) {
        return value == null ? null : new MovementDate(value.toLocalDateTime());
    }

    @Named("toMovementType")
    default MovementType toMovementType(Object value) {
        if (value == null) return null;
        return new MovementType(value.toString());
    }

    @Named("toAmount")
    default Amount toAmount(Double value) {
        return value == null ? null : new Amount(value);
    }

    @Named("fromMovementId")
    default UUID fromMovementId(MovementId id) {
        return id == null ? null : UUID.fromString(id.getValue());
    }

    @Named("fromAccountId")
    default UUID fromAccountId(AccountId id) {
        return id == null ? null : UUID.fromString(id.getValue());
    }

    @Named("fromMovementDate")
    default OffsetDateTime fromMovementDate(MovementDate date) {
        return date == null ? null : date.getValue().atOffset(ZoneOffset.UTC);
    }

    @Named("fromMovementType")
    default MovementDto.MovementTypeEnum fromMovementType(MovementType type) {
        return type == null ? null : MovementDto.MovementTypeEnum.fromValue(type.getValue());
    }

    @Named("fromAmount")
    default Double fromAmount(Amount amount) {
        return amount == null ? null : amount.getValue();
    }

    @Named("fromBalance")
    default Double fromBalance(Balance balance) {
        return balance == null ? null : balance.getValue();
    }

    @Named("fromStatus")
    default Boolean fromStatus(Status status) {
        return status == null ? null : status.getValue();
    }
}