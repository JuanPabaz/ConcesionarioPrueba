package com.pruebatecnica.concesionario.exceptions;

import com.pruebatecnica.concesionario.exceptions.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCreateRequest.class)
    public ResponseEntity<Object> handleBadCreateRequest(BadCreateRequest exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
