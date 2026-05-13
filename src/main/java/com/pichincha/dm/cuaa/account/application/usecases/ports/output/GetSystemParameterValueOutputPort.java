package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import reactor.core.publisher.Mono;

public interface GetSystemParameterValueOutputPort {
    Mono<String> getValueByCode(String code);
}