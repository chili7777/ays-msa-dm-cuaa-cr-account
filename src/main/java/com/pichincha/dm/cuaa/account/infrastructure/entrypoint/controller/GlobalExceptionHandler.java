package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.domain.entities.DuplicateResourceException;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.UnauthorizedException;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.ErrorListDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.ErrorModelDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebInputException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorModelDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorModelDto error = new ErrorModelDto("Not Found", ex.getMessage(), "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        // Check if it's a business validation (e.g. from AccountCreator) or a direct lookup
        if (ex.getMessage().contains("El cliente con ID") || ex.getMessage().contains("no existe")) {
            error.setTitle("Bad Request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorModelDto> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorModelDto error = new ErrorModelDto("Unauthorized", ex.getMessage(), "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorModelDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorModelDto error = new ErrorModelDto("Bad Request", ex.getMessage(), "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorModelDto> handleDuplicateResourceException(DuplicateResourceException ex) {
        ErrorModelDto error = new ErrorModelDto("Conflict", ex.getMessage(), "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorModelDto> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ErrorListDto> errors = ex.getConstraintViolations().stream()
                .map(violation -> {
                    ErrorListDto errorListDto = new ErrorListDto();
                    errorListDto.setCode("ERR-VALIDATION");
                    errorListDto.setMessage(violation.getPropertyPath() + ": " + violation.getMessage());
                    errorListDto.setBusinessMessage("Error de validación en el campo " + violation.getPropertyPath());
                    return errorListDto;
                })
                .collect(Collectors.toList());

        ErrorModelDto error = new ErrorModelDto("Bad Request", "Validation error in request payload", "N/A", "/api/v1");
        error.setErrors(errors);
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorModelDto> handleWebExchangeBindException(WebExchangeBindException ex) {
        List<ErrorListDto> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    ErrorListDto errorListDto = new ErrorListDto();
                    errorListDto.setCode("ERR-VALIDATION");
                    errorListDto.setMessage(fieldError.getField() + ": " + fieldError.getDefaultMessage());
                    errorListDto.setBusinessMessage("Error de validación en el campo " + fieldError.getField());
                    return errorListDto;
                })
                .collect(Collectors.toList());

        ErrorModelDto error = new ErrorModelDto("Bad Request", "Validation error in request payload", "N/A", "/api/v1");
        error.setErrors(errors);
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorModelDto> handleServerWebInputException(ServerWebInputException ex) {
        ErrorModelDto error = new ErrorModelDto("Bad Request", "Invalid input: " + ex.getReason(), "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorModelDto> handleNoResourceFoundException(NoResourceFoundException ex) {
        ErrorModelDto error = new ErrorModelDto("Not Found", "The requested resource was not found", "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorModelDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Data integrity violation", ex);
        String message = "Error de integridad de datos. Posible duplicado o valor inválido.";
        String detail = ex.getMessage() != null ? ex.getMessage() : "";
        
        if (detail.contains("uk_clients_identification")) {
            message = "La identificación ya existe";
        } else if (detail.contains("uk_accounts_number")) {
            message = "El número de cuenta ya existe";
        } else if (detail.toLowerCase().contains("foreign key") || detail.toLowerCase().contains("referential integrity")) {
            message = "No se puede eliminar o modificar el registro porque tiene información relacionada vinculada.";
        }
        
        ErrorModelDto error = new ErrorModelDto("Bad Request", message, "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModelDto> handleGeneralException(Exception ex) {
        log.error("Unexpected error", ex);
        ErrorModelDto error = new ErrorModelDto("Internal Server Error", "An unexpected error occurred", "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}