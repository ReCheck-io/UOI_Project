package io.recheck.uoi.exceptions;

public class GeneralErrorException extends Exception{
    public GeneralErrorException(){
        super();
    }

    public GeneralErrorException(String message) {
        super(message);
    }
}
