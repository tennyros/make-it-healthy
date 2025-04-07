package com.github.tennyros.makeithealthy.exception;

public class MealNotFoundException extends RuntimeException {

    public MealNotFoundException(String message) {
        super(message);
    }

    public MealNotFoundException(Throwable cause) {
        super(cause);
    }
}
