package com.pruebatecnica.concesionario.exceptions;

public class BadCreateRequest extends RuntimeException {

    public BadCreateRequest(String message) {
        super(message);
    }
}
