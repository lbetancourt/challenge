package com.mercadolibre.challengue.ip.domain;

public class IPForbiddenException extends RuntimeException{
    public IPForbiddenException(String message) {
        super(message);
    }
}
