package com.github.tennyros.makeithealthy.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for creating a new meal")
public record MealCreateDto(

        @Schema(description = "Name of the meal", example = "Greek Yogurt with Berries")
        @NotBlank(message = "Meal name must be defined")
        String name,

        @Schema(description = "Calories per serving (kcal)", example = "150.5")
        @DecimalMin(value = "0.0", message = "The calories count must be valid")
        @DecimalMax(value = "10000.0", message = "The calories count must be valid")
        double caloriesPerSaving,

        @Schema(description = "Protein content per serving (grams)", example = "12.3")
        @DecimalMin(value = "0.0", message = "The protein count must be valid")
        @DecimalMax(value = "2000.0", message = "The protein count must be valid")
        double proteins,

        @Schema(description = "Carbohydrates per serving (grams)", example = "20.1")
        @DecimalMin(value = "0.0", message = "The carbs count must be valid")
        @DecimalMax(value = "2000.0", message = "The carbs count must be valid")
        double carbs,

        @Schema(description = "Fats per serving (grams)", example = "5.7")
        @DecimalMin(value = "0.0", message = "The fats count must be valid")
        @DecimalMax(value = "2000.0", message = "The fats count must be valid")
        double fats

) {
}
