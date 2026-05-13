package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetSystemParameterValueOutputPort;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.SystemParameterEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.SystemParameterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class SystemParameterAdapter implements GetSystemParameterValueOutputPort {

    private final SystemParameterJpaRepository repository;

    @Override
    public Mono<String> getValueByCode(String code) {
        return Mono.fromCallable(() -> repository.findByCode(code))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional -> optional.map(SystemParameterEntity::getValue).map(Mono::just).orElse(Mono.empty()));
    }
}