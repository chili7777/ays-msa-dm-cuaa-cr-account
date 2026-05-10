package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CuentaCreateRequestDto;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class CuentasController implements CuentasApi {

	private static final ResponseEntity<Void> CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build();

	@Override
	public Mono<ResponseEntity<Void>> cuentasPost(UUID xGuid,
												  String xApp,
												  Mono<CuentaCreateRequestDto> cuentaCreateRequestDto,
												  ServerWebExchange exchange) {
		return cuentaCreateRequestDto.map(request -> CREATED_RESPONSE);
	}

}