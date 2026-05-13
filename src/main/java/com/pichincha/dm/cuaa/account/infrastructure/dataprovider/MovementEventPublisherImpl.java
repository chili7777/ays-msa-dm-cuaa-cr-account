package com.pichincha.dm.cuaa.account.infrastructure.dataprovider;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.MovementEventPublisher;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class MovementEventPublisherImpl implements MovementEventPublisher {

    private final Sinks.Many<Movement> sink;

    public MovementEventPublisherImpl() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @Override
    public void publish(Movement movement) {
        sink.tryEmitNext(movement);
    }

    @Override
    public Flux<Movement> getMovementStream() {
        return sink.asFlux();
    }
}