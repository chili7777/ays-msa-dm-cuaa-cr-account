package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.domain.usecases.ports.input.CreateAccountInputPort;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.AccountHttpRequestMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AccountsController implements AccountsApi {

	private static final ResponseEntity<Void> CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build();
	private final CreateAccountInputPort createAccountUseCase;
	private final AccountHttpRequestMapper accountHttpRequestMapper;

	@Override
	public Mono<ResponseEntity<Void>> createAccount(UUID xGuid,
											  String xApp,
											  Mono<AccountCreateRequestDto> accountCreateRequestDto,
											  ServerWebExchange exchange) {
		return accountCreateRequestDto
				.map(accountHttpRequestMapper::toAccount)
				.flatMap(createAccountUseCase::createAccount)
				.thenReturn(CREATED_RESPONSE);
	}

}