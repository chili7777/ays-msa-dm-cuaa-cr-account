package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementMother;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
final class MovementByAccountListerTest {

    @Mock
    private ListMovementsOutputPort movementPersistence;

    @InjectMocks
    private MovementByAccountLister movementByAccountLister;

    @Test
    void given_accountId_when_listMovementsByAccount_then_returnMovements() {
        AccountId accountId = AccountIdMother.random();
        Movement movement = MovementMother.random();

        when(movementPersistence.findMovementsByAccountId(accountId)).thenReturn(Flux.just(movement));

        List<Movement> movements = movementByAccountLister.listMovementsByAccount(accountId).collectList().block();

        assert movements != null;
        assertEquals(1, movements.size());
        assertEquals(movement, movements.get(0));
    }
}