package com.github.tennyros.makeithealthy.http.rest;

import com.github.tennyros.makeithealthy.dto.request.FoodIntakeRequest;
import com.github.tennyros.makeithealthy.dto.response.FoodIntakeResponse;
import com.github.tennyros.makeithealthy.exception.FoodIntakeNotFoundException;
import com.github.tennyros.makeithealthy.service.FoodIntakeService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/intakes")
public class FoodIntakeRestController {

    private final FoodIntakeService foodIntakeService;

    @Operation(summary = "Create food intake record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created food intake",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FoodIntakeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<FoodIntakeResponse> createFoodIntake(@Valid @RequestBody FoodIntakeRequest request) {
        return ResponseEntity.ok(foodIntakeService.createFoodIntake(request));
    }

    @Operation(summary = "Get food intake record by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food intake found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FoodIntakeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Food intake not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FoodIntakeResponse> getFoodIntake(@PathVariable Long id) {
        FoodIntakeResponse foodInTakeResponse = foodIntakeService.getFoodIntake(id)
                .orElseThrow(() -> new FoodIntakeNotFoundException("FoodInTake with id " + id + " not found"));
        return ResponseEntity.ok(foodInTakeResponse);
    }

}
