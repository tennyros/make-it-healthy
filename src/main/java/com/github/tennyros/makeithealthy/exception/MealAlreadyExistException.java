package com.github.tennyros.makeithealthy.exception;

public class MealAlreadyExistException extends RuntimeException {

    public MealAlreadyExistException(String message) {
        super(message);
    }

    public MealAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
