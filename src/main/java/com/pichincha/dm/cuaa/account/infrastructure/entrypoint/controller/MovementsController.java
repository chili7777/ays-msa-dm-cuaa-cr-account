package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.*;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.*;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.MovementHttpRequestMapper;
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

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
public class MovementsController implements MovementsApi {

    private static final ResponseEntity<Void> CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build();
    private static final ResponseEntity<Void> NO_CONTENT_RESPONSE = ResponseEntity.noContent().build();

    private final CreateMovementInputPort createMovementUseCase;
    private final ListMovementsInputPort listMovementsUseCase;
    private final ListMovementsByAccountInputPort listMovementsByAccountUseCase;
    private final GetMovementByIdInputPort getMovementByIdUseCase;
    private final ReplaceMovementInputPort replaceMovementUseCase;
    private final PatchMovementInputPort patchMovementUseCase;
    private final DeleteMovementInputPort deleteMovementUseCase;
    private final MovementHttpRequestMapper movementMapper;

    @Override
    public Mono<ResponseEntity<Void>> createMovement(UUID xGuid, String xApp, Mono<MovementCreateRequestDto> movementCreateRequestDto, ServerWebExchange exchange) {
        return movementCreateRequestDto
                .map(movementMapper::toMovement)
                .flatMap(createMovementUseCase::createMovement)
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteMovement(UUID xGuid, String xApp, UUID movementId, ServerWebExchange exchange) {
        return deleteMovementUseCase.deleteMovement(new MovementId(movementId.toString()))
                .thenReturn(NO_CONTENT_RESPONSE);
    }

    @Override
    public Mono<ResponseEntity<MovementDto>> getMovementById(UUID xGuid, String xApp, UUID movementId, ServerWebExchange exchange) {
        return getMovementByIdUseCase.getMovementById(new MovementId(movementId.toString()))
                .map(movementMapper::toMovementDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Movement not found: " + movementId)));
    }

    @Override
    public Mono<ResponseEntity<Flux<MovementDto>>> listMovements(UUID xGuid, String xApp, UUID accountId, java.time.LocalDate fromDate, java.time.LocalDate toDate, String movementType, ServerWebExchange exchange) {
        Flux<MovementDto> movementsFlux;
        if (accountId != null) {
            movementsFlux = listMovementsByAccountUseCase.listMovementsByAccount(new AccountId(accountId.toString()))
                    .map(movementMapper::toMovementDto);
        } else {
            movementsFlux = listMovementsUseCase.listMovements()
                    .map(movementMapper::toMovementDto);
        }
        return Mono.just(ResponseEntity.ok(movementsFlux));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchMovement(UUID xGuid, String xApp, UUID movementId, Mono<MovementPatchRequestDto> movementPatchRequestDto, ServerWebExchange exchange) {
        return movementPatchRequestDto
                .map(movementMapper::toMovement)
                .flatMap(m -> patchMovementUseCase.patchMovement(new MovementId(movementId.toString()), m))
                .thenReturn(NO_CONTENT_RESPONSE);
    }

    @Override
    public Mono<ResponseEntity<Void>> replaceMovement(UUID xGuid, String xApp, UUID movementId, Mono<MovementUpdateRequestDto> movementUpdateRequestDto, ServerWebExchange exchange) {
        return movementUpdateRequestDto
                .map(movementMapper::toMovement)
                .flatMap(m -> replaceMovementUseCase.replaceMovement(new MovementId(movementId.toString()), m))
                .thenReturn(NO_CONTENT_RESPONSE);
    }
}