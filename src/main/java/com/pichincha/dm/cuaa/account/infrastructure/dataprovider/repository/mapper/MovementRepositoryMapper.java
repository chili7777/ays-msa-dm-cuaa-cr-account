package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Amount;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Balance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Description;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementDate;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.ValueObject;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.MovementEntity;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MovementRepositoryMapper {

    @Mapping(target = "movementId", source = "movementId", qualifiedByName = "fromMovementIdToString")
    @Mapping(target = "accountId", source = "accountId", qualifiedByName = "fromAccountIdToString")
    @Mapping(target = "movementDate", source = "movementDate", qualifiedByName = "fromValueObjectToLocalDateTime")
    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "fromValueObjectToString")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "fromValueObjectToDouble")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "fromValueObjectToDouble")
    @Mapping(target = "status", source = "status", qualifiedByName = "fromValueObjectToBoolean")
    @Mapping(target = "description", source = "description", qualifiedByName = "fromValueObjectToString")
    MovementEntity toMovementEntity(Movement movement);

    @Mapping(target = "movementId", source = "movementId", qualifiedByName = "toMovementId")
    @Mapping(target = "accountId", source = "accountId", qualifiedByName = "toAccountId")
    @Mapping(target = "movementDate", source = "movementDate", qualifiedByName = "toMovementDate")
    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "toMovementType")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "toAmount")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "toBalance")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatus")
    @Mapping(target = "description", source = "description", qualifiedByName = "toDescription")
    Movement toMovement(MovementEntity movementEntity);

    @Named("fromMovementIdToString")
    default String fromMovementIdToString(MovementId movementId) {
        return movementId == null ? null : movementId.getValue();
    }

    @Named("fromAccountIdToString")
    default String fromAccountIdToString(AccountId accountId) {
        return accountId == null ? null : accountId.getValue();
    }

    @Named("fromValueObjectToLocalDateTime")
    default LocalDateTime fromValueObjectToLocalDateTime(ValueObject<LocalDateTime> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
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

    @Named("toMovementId")
    default MovementId toMovementId(String movementId) {
        return movementId == null ? null : new MovementId(movementId);
    }

    @Named("toAccountId")
    default AccountId toAccountId(String accountId) {
        return accountId == null ? null : new AccountId(accountId);
    }

    @Named("toMovementDate")
    default MovementDate toMovementDate(LocalDateTime movementDate) {
        return movementDate == null ? null : new MovementDate(movementDate);
    }

    @Named("toMovementType")
    default MovementType toMovementType(String movementType) {
        return movementType == null ? null : new MovementType(movementType);
    }

    @Named("toAmount")
    default Amount toAmount(Double amount) {
        return amount == null ? null : new Amount(amount);
    }

    @Named("toBalance")
    default Balance toBalance(Double balance) {
        return balance == null ? null : new Balance(balance);
    }

    @Named("toStatus")
    default Status toStatus(Boolean status) {
        return status == null ? null : new Status(status);
    }

    @Named("toDescription")
    default Description toDescription(String description) {
        return new Description(description);
    }
}