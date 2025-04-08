package com.github.tennyros.makeithealthy.http.rest;

import com.github.tennyros.makeithealthy.dto.request.FoodIntakeRequest;
import com.github.tennyros.makeithealthy.dto.response.FoodIntakeResponse;
import com.github.tennyros.makeithealthy.exception.FoodIntakeNotFoundException;
import com.github.tennyros.makeithealthy.service.FoodIntakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food-in-take")
public class FoodIntakeRestController {

    private final FoodIntakeService foodIntakeService;

    @PostMapping
    public ResponseEntity<FoodIntakeResponse> createFoodIntake(@Valid @RequestBody FoodIntakeRequest request) {
        return ResponseEntity.ok(foodIntakeService.createFoodIntake(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodIntakeResponse> getFoodIntake(@PathVariable Long id) {
        FoodIntakeResponse foodInTakeResponse = foodIntakeService.getFoodIntake(id)
                .orElseThrow(() -> new FoodIntakeNotFoundException("FoodInTake with id " + id + " not found"));
        return ResponseEntity.ok(foodInTakeResponse);
    }

}
