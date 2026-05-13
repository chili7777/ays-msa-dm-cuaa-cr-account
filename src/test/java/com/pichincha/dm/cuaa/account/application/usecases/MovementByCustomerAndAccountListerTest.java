package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementMother;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
final class MovementByCustomerAndAccountListerTest {

    @Mock
    private ListMovementsOutputPort movementPersistence;

    @InjectMocks
    private MovementByCustomerAndAccountLister movementByCustomerAndAccountLister;

    @Test
    void given_customerIdAndAccountId_when_listMovementsByCustomerAndAccount_then_returnMovements() {
        CustomerId customerId = CustomerIdMother.random();
        AccountId accountId = AccountIdMother.random();
        Movement movement = MovementMother.random();

        when(movementPersistence.findMovementsByCustomerAndAccountId(customerId, accountId))
                .thenReturn(Flux.just(movement));

        List<Movement> movements = movementByCustomerAndAccountLister
                .listMovementsByCustomerAndAccount(customerId, accountId, null, null, null)
                .collectList()
                .block();

        assert movements != null;
        assertEquals(1, movements.size());
        assertEquals(movement, movements.get(0));
    }
}