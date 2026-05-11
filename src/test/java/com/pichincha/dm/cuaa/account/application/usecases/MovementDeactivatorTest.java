package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteMovementOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementIdMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class MovementDeactivatorTest {

    @Mock
    private DeleteMovementOutputPort movementPersistence;

    @InjectMocks
    private MovementDeactivator movementDeactivator;

    @Test
    void given_existingMovementId_when_deleteMovement_then_deactivateInPersistence() {
        MovementId movementId = MovementIdMother.random();

        when(movementPersistence.deactivate(movementId)).thenReturn(Mono.empty());

        movementDeactivator.deleteMovement(movementId).block();

        verify(movementPersistence).deactivate(movementId);
    }
}