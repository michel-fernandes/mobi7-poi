package br.com.j38.poi.exception;

import java.io.Serial;

public class BusinessRulesException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    
    public BusinessRulesException(String message){
        super(message);
    }
}