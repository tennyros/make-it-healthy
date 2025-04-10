package com.github.tennyros.makeithealthy.http.advice;

import com.github.tennyros.makeithealthy.exception.BaseAlreadyExistsException;
import com.github.tennyros.makeithealthy.exception.BaseNotFoundException;
import com.github.tennyros.makeithealthy.exception.FoodIntakeNotFoundException;
import com.github.tennyros.makeithealthy.exception.UserAlreadyExistsException;
import com.github.tennyros.makeithealthy.exception.MealAlreadyExistException;
import com.github.tennyros.makeithealthy.exception.MealNotFoundException;
import com.github.tennyros.makeithealthy.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, MealNotFoundException.class, FoodIntakeNotFoundException.class})
    public ProblemDetail handleUserNotFound(BaseNotFoundException ex, HttpServletRequest request) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage(), "Not found", request);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, MealAlreadyExistException.class})
    public ProblemDetail handleEmailAlreadyExists(BaseAlreadyExistsException ex, HttpServletRequest request) {
        return buildProblemDetail(HttpStatus.CONFLICT, ex.getMessage(), "Conflict", request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {

        ProblemDetail problemDetail = buildProblemDetail(HttpStatus.BAD_REQUEST, "One or more fields are invalid",
                "Validation failed", request);

        List<Map<String, Object>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    Map<String, Object> errorDetail = new HashMap<>();
                    errorDetail.put("field", error.getField());
                    errorDetail.put("message", error.getDefaultMessage());
                    errorDetail.put("rejectedValue", error.getRejectedValue());
                    return errorDetail;
                })
                .toList();

        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String message,
                                             String title, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;
    }
}

