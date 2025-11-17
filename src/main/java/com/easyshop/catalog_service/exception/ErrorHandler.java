package com.easyshop.catalog_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public Mono<ResponseEntity<Object>> handle(ProductAlreadyExistsException ex, ServerWebExchange exchange) {
        logger.warn(ex.getMessage());
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        return Mono.just(new ResponseEntity<>(pd, HttpStatus.BAD_REQUEST));
    }
}
