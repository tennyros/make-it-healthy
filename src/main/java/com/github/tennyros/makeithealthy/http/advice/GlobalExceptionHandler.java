package com.github.tennyros.makeithealthy.http.advice;

import com.github.tennyros.makeithealthy.exception.EmailAlreadyExistsException;
import com.github.tennyros.makeithealthy.exception.MealAlreadyExistException;
import com.github.tennyros.makeithealthy.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException ex) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, ex, "User not found");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return buildProblemDetail(HttpStatus.CONFLICT, ex, "Email already exists");
    }

    @ExceptionHandler(MealAlreadyExistException.class)
    public ProblemDetail handleMealAlreadyExists(MealAlreadyExistException ex) {
        return buildProblemDetail(HttpStatus.CONFLICT, ex, "Meal already exists");
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, Exception ex, String description) {
//                problemDetail.setProperty("description", description);
        return ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    }
}
