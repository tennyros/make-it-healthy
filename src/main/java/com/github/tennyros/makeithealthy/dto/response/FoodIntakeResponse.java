package com.github.tennyros.makeithealthy.dto.response;

import com.github.tennyros.makeithealthy.entity.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FoodIntakeResponse(

        @Schema(description = "ID of the food intake")
        @NotNull Long id,

        @Schema(description = "Type of meal",
                allowableValues = {"BREAKFAST", "LUNCH", "DINNER", "SNACK"})
        @NotNull MealType mealType

) {
}
