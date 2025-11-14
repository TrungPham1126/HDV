package com.example.demo.exception;

public class IdInvalidException extends RuntimeException {
    public IdInvalidException(String message) {
        super(message);
    }
}