package com.github.tennyros.makeithealthy.exception;

public class FoodIntakeNotFoundException extends RuntimeException {

    public FoodIntakeNotFoundException(String message) {
        super(message);
    }

    public FoodIntakeNotFoundException(Throwable cause) {
        super(cause);
    }
}
