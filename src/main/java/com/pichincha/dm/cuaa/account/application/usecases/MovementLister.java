package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListMovementsInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class MovementLister implements ListMovementsInputPort {

    private final ListMovementsOutputPort repository;

    @Override
    public Flux<Movement> listMovements() {
        return repository.findAll();
    }
}