package com.github.tennyros.makeithealthy.http.rest;

import com.github.tennyros.makeithealthy.dto.request.MealRequest;
import com.github.tennyros.makeithealthy.dto.response.MealResponse;
import com.github.tennyros.makeithealthy.dto.response.UserResponse;
import com.github.tennyros.makeithealthy.exception.MealNotFoundException;
import com.github.tennyros.makeithealthy.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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

    @PostMapping
    public ResponseEntity<MealResponse> create(@RequestBody MealRequest meal) {
        MealResponse mealResponse = mealService.create(meal);

        URI location = URI.create("/meals/" + mealResponse.id());
        return ResponseEntity
                .created(location)
                .body(mealResponse);
    }

    @Operation(summary = "Get meal by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meal found",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "Meal not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> get(@PathVariable Long id) {
        MealResponse response = mealService.getMealById(id)
                .orElseThrow(() -> new MealNotFoundException("Meal with id " + id + " not found"));
        return ResponseEntity.ok(response);
    }

}
