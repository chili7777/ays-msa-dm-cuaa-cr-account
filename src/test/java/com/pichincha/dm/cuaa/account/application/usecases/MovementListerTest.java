package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementMother;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
final class MovementListerTest {

    @Mock
    private ListMovementsOutputPort movementPersistence;

    @InjectMocks
    private MovementLister movementLister;

    @Test
    void given_existingMovements_when_listMovements_then_returnAllMovements() {
        Movement movement1 = MovementMother.random();
        Movement movement2 = MovementMother.random();
        List<Movement> expectedMovements = List.of(movement1, movement2);

        when(movementPersistence.findAllMovements()).thenReturn(Flux.fromIterable(expectedMovements));

        List<Movement> actualMovements = movementLister.listMovements(null, null, null).collectList().block();

        assertEquals(expectedMovements, actualMovements);
        verify(movementPersistence).findAllMovements();
    }
}