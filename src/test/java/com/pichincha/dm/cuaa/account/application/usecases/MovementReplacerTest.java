package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceMovementOutputPort;
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
final class MovementReplacerTest {

    @Mock
    private ReplaceMovementOutputPort movementPersistence;

    @InjectMocks
    private MovementReplacer movementReplacer;

    @Test
    void given_validMovementData_when_replaceMovement_then_updateInPersistence() {
        MovementId movementId = MovementIdMother.random();
        Movement movement = MovementMother.random();

        when(movementPersistence.update(movementId, movement)).thenReturn(Mono.empty());

        movementReplacer.replaceMovement(movementId, movement).block();

        verify(movementPersistence).update(movementId, movement);
    }
}