package com.mjc.school.service.exception;

public class ValidatorException extends RuntimeException {

    private final String errorCode;

    public ValidatorException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public String getErrorCode() {
        return errorCode;
    }
}