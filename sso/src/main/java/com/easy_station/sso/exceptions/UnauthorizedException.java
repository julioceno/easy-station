package com.easy_station.sso.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Não autorizado.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
