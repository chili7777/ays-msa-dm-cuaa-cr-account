package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class AccountsController implements AccountsApi {

	private static final ResponseEntity<Void> CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build();

	@Override
	public Mono<ResponseEntity<Void>> createAccount(UUID xGuid,
											  String xApp,
											  Mono<AccountCreateRequestDto> accountCreateRequestDto,
											  ServerWebExchange exchange) {
		return accountCreateRequestDto.map(request -> CREATED_RESPONSE);
	}

}