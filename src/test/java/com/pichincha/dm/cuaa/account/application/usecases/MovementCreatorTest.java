package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateMovementOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class MovementCreatorTest {

    @Mock
    private CreateMovementOutputPort movementPersistence;

    @InjectMocks
    private MovementCreator movementCreator;

    @Test
    void given_validMovementData_when_createMovement_then_persistMovement() {
        Movement movement = MovementMother.random();

        when(movementPersistence.save(movement)).thenReturn(Mono.empty());

        movementCreator.createMovement(movement).block();

        verify(movementPersistence, atLeastOnce()).save(movement);
    }

    @Test
    void given_movementWithoutId_when_createMovement_then_generateIdAndPersist() {
        Movement movementWithoutId = new Movement(
                null,
                MovementMother.random().accountId(),
                MovementMother.random().movementDate(),
                MovementMother.random().movementType(),
                MovementMother.random().amount()
        );

        when(movementPersistence.save(any(Movement.class))).thenReturn(Mono.empty());

        movementCreator.createMovement(movementWithoutId).block();

        verify(movementPersistence).save(argThat(mov -> mov.movementId() != null));
    }
}