package com.numble.carot.exception;

public class InvalidResourceException extends RuntimeException{
    public InvalidResourceException() {
        super();
    }

    public InvalidResourceException(String message) {
        super(message);
    }

    public InvalidResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
