package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.ErrorListDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.ErrorModelDto;
import jakarta.validation.ConstraintViolationException;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorModelDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorModelDto error = new ErrorModelDto("Bad Request", ex.getMessage(), "N/A", "/api/v1");
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModelDto> handleGeneralException(Exception ex) {
        log.error("Unexpected error", ex);
        ErrorModelDto error = new ErrorModelDto("Internal Server Error", "An unexpected error occurred", "N/A", "/api/v1");
        error.setComponent("TX-ACC-001");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}