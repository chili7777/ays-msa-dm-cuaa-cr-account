package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetMovementByIdOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class MovementFinderTest {

    @Mock
    private GetMovementByIdOutputPort movementPersistence;

    @InjectMocks
    private MovementFinder movementFinder;

    @Test
    void given_existingMovementId_when_getMovementById_then_returnMovement() {
        MovementId movementId = MovementIdMother.random();
        Movement expectedMovement = MovementMother.withId(movementId);

        when(movementPersistence.findById(movementId)).thenReturn(Mono.just(expectedMovement));

        Movement actualMovement = movementFinder.getMovementById(movementId).block();

        assertEquals(expectedMovement, actualMovement);
        verify(movementPersistence).findById(movementId);
    }
}