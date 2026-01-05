package com.herve.carLink.exception;

public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException(String message) {
        super(message);
    }
}
