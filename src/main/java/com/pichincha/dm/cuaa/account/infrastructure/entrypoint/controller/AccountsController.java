package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.CreateAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.DeleteAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.GetAccountByIdInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListAccountsInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListMovementsByAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.PatchAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ReplaceAccountInputPort;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountUpdateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.AccountHttpRequestMapper;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.MovementHttpRequestMapper;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated
public class AccountsController implements AccountsApi {

	private static final ResponseEntity<Void> CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build();
	private static final ResponseEntity<Void> NO_CONTENT_RESPONSE = ResponseEntity.noContent().build();
	private final CreateAccountInputPort createAccountUseCase;
	private final ListAccountsInputPort listAccountsUseCase;
	private final GetAccountByIdInputPort getAccountByIdUseCase;
	private final ReplaceAccountInputPort replaceAccountUseCase;
	private final PatchAccountInputPort patchAccountUseCase;
	private final DeleteAccountInputPort deleteAccountUseCase;
	private final ListMovementsByAccountInputPort listMovementsByAccountUseCase;
	private final AccountHttpRequestMapper accountHttpRequestMapper;
	private final MovementHttpRequestMapper movementHttpRequestMapper;

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

	@Override
	public Mono<ResponseEntity<Flux<AccountDto>>> listAccounts(UUID xGuid,
															   String xApp,
															   UUID accountId,
															   UUID clientId,
															   String accountNumber,
															   Boolean status,
															   ServerWebExchange exchange) {
		Flux<AccountDto> accountsFlux;
		if (accountId != null) {
			accountsFlux = getAccountByIdUseCase.getAccountById(new AccountId(accountId.toString()))
					.map(accountHttpRequestMapper::toAccountDto)
					.flux();
		} else {
			accountsFlux = listAccountsUseCase.listAccounts()
					.map(accountHttpRequestMapper::toAccountDto);
		}

		if (clientId != null) {
			accountsFlux = accountsFlux.filter(account -> account.getClientId().equals(clientId));
		}

		if (accountNumber != null && !accountNumber.isBlank()) {
			accountsFlux = accountsFlux.filter(account -> account.getAccountNumber().equals(accountNumber));
		}

		if (status != null) {
			accountsFlux = accountsFlux.filter(account -> account.getStatus().equals(status));
		}

		return Mono.just(ResponseEntity.ok(accountsFlux));
	}

	@Override
	public Mono<ResponseEntity<Flux<MovementDto>>> listMovementsByAccount(UUID xGuid,
																		  String xApp,
																		  UUID accountId,
																		  ServerWebExchange exchange) {
		return Mono.just(ResponseEntity.ok(
				listMovementsByAccountUseCase.listMovementsByAccount(new AccountId(accountId.toString()))
						.map(movementHttpRequestMapper::toMovementDto)));
	}

	@Override
	public Mono<ResponseEntity<AccountDto>> getAccountById(UUID xGuid,
														  String xApp,
														  UUID accountId,
														  ServerWebExchange exchange) {
		return getAccountByIdUseCase.getAccountById(new AccountId(accountId.toString()))
				.map(accountHttpRequestMapper::toAccountDto)
				.map(ResponseEntity::ok)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found: " + accountId)));
	}

	@Override
	public Mono<ResponseEntity<Void>> replaceAccount(UUID xGuid,
													String xApp,
													UUID accountId,
													Mono<AccountUpdateRequestDto> accountUpdateRequestDto,
													ServerWebExchange exchange) {
		return accountUpdateRequestDto
				.map(accountHttpRequestMapper::toAccount)
				.flatMap(account -> replaceAccountUseCase.replaceAccount(new AccountId(accountId.toString()), account))
				.thenReturn(NO_CONTENT_RESPONSE);
	}

	@Override
	public Mono<ResponseEntity<Void>> patchAccount(UUID xGuid,
												  String xApp,
												  UUID accountId,
												  Mono<AccountPatchRequestDto> accountPatchRequestDto,
												  ServerWebExchange exchange) {
		return accountPatchRequestDto
				.map(accountHttpRequestMapper::toAccount)
				.flatMap(account -> patchAccountUseCase.patchAccount(new AccountId(accountId.toString()), account))
				.thenReturn(NO_CONTENT_RESPONSE);
	}

	@Override
	public Mono<ResponseEntity<Void>> deleteAccount(UUID xGuid,
												   String xApp,
												   UUID accountId,
												   ServerWebExchange exchange) {
		return deleteAccountUseCase.deleteAccount(new AccountId(accountId.toString()))
				.thenReturn(NO_CONTENT_RESPONSE);
	}
}