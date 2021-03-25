package io.recheck.uoi.exceptions;

public class ValidationErrorException extends Exception{
    public ValidationErrorException(String message){
        super(message);
    }
}
