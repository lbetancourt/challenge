package com.mercadolibre.challengue.ip.infrastructure.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = IPController.class)
public class IPControllerAdvice {
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<String> handleException(final Throwable exception) {
        log.error("Internal server error {}", exception.getMessage());
        return ResponseEntity.internalServerError()
                .body("Internal server error");
    }
}
