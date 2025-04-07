package com.github.tennyros.makeithealthy.dto.response.reporting;

import com.github.tennyros.makeithealthy.entity.MealType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Meal consumption summary for specific eating time")
public record MealTimeSummary(

        @Schema(description = "Type of meal",
                allowableValues = {"BREAKFAST", "LUNCH", "DINNER", "SNACK"})
        MealType mealType,

        @Schema(description = "Total calories in meal", example = "420.5")
        double calories,

        @Schema(description = "Names of consumed dishes")
        List<String> mealNames
) {

}
