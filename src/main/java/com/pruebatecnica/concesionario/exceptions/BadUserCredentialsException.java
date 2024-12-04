package com.pruebatecnica.concesionario.exceptions;

public class BadUserCredentialsException extends RuntimeException{

    public BadUserCredentialsException(String message){
        super(message);
    }

}
