package com.helpflow.tickets.infrastructure.input.rest;

import com.helpflow.tickets.domain.exception.InvalidTicketTransitionException;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTicketTransitionException.class)
    ResponseEntity<ProblemDetail> invalidTransition(RuntimeException ex, WebRequest request) {
        return problem(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<ProblemDetail> notFound(RuntimeException ex, WebRequest request) {
        return problem(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ProblemDetail> badRequest(RuntimeException ex, WebRequest request) {
        return problem(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<ProblemDetail> unavailable(RuntimeException ex, WebRequest request) {
        return problem(HttpStatus.BAD_GATEWAY, ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException ex, WebRequest request) {
        String detail = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).findFirst().orElse("Solicitud inválida");
        return problem(HttpStatus.BAD_REQUEST, new IllegalArgumentException(detail), request);
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, Exception ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, ex.getMessage() == null ? status.getReasonPhrase() : ex.getMessage());
        detail.setTitle(status.getReasonPhrase());
        detail.setProperty("timestamp", Instant.now().toString());
        detail.setProperty("traceId", MDC.get("traceId"));
        detail.setInstance(java.net.URI.create(request.getDescription(false).replace("uri=", "")));
        return ResponseEntity.status(status).body(detail);
    }
}
