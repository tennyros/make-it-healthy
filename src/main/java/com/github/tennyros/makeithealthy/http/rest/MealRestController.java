package com.github.tennyros.makeithealthy.http.rest;

import com.github.tennyros.makeithealthy.dto.request.MealRequest;
import com.github.tennyros.makeithealthy.dto.response.MealResponse;
import com.github.tennyros.makeithealthy.exception.MealNotFoundException;
import com.github.tennyros.makeithealthy.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meals")
public class MealRestController {

    private final MealService mealService;

    @Operation(summary = "Create a new meal")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meal successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MealResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Meal already exists",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<MealResponse> createMeal(@Valid @RequestBody MealRequest meal) {
        MealResponse mealResponse = mealService.createMeal(meal);

        URI location = URI.create("/meals/" + mealResponse.id());
        return ResponseEntity
                .created(location)
                .body(mealResponse);
    }

    @Operation(summary = "Get meal by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meal found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MealResponse.class))),
            @ApiResponse(responseCode = "404", description = "Meal not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> getMeal(@PathVariable Long id) {
        MealResponse response = mealService.getMeal(id)
                .orElseThrow(() -> new MealNotFoundException("Meal with id " + id + " not found"));
        return ResponseEntity.ok(response);
    }

}
