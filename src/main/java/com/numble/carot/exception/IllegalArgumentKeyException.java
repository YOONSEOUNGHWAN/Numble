package com.numble.carot.exception;

public class IllegalArgumentKeyException extends IllegalArgumentException{
    public IllegalArgumentKeyException() {
    }

    public IllegalArgumentKeyException(String s) {
        super(s);
    }

    public IllegalArgumentKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
