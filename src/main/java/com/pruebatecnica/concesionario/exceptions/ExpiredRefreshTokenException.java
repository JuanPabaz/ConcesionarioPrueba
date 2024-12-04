package com.pruebatecnica.concesionario.exceptions;

public class ExpiredRefreshTokenException extends RuntimeException{

    public ExpiredRefreshTokenException(String message){
        super(message);
    }

}
