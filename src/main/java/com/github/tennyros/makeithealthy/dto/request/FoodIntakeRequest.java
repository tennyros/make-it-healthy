package com.github.tennyros.makeithealthy.dto.request;

import com.github.tennyros.makeithealthy.entity.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Request for creating a new food intake entry")
public record FoodIntakeRequest(

        @Schema(description = "ID of the user for whom the food intake is created")
        @NotNull Long userId,

        @Schema(description = "Type of meal",
                allowableValues = {"BREAKFAST", "LUNCH", "DINNER", "SNACK"})
        @NotNull MealType mealType,

        @Schema(description = "List of meal IDs associated with this food intake")
        @NotEmpty List<Long> mealIds

) {
}
