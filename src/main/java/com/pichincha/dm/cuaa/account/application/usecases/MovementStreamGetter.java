package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.GetMovementStreamInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.MovementEventPublisher;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class MovementStreamGetter implements GetMovementStreamInputPort {

    private final MovementEventPublisher eventPublisher;

    @Override
    public Flux<Movement> getMovementStream() {
        return eventPublisher.getMovementStream();
    }
}