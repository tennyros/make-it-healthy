package com.github.tennyros.makeithealthy.dto.response;

import com.github.tennyros.makeithealthy.entity.MealType;
import jakarta.validation.constraints.NotNull;

public record FoodIntakeResponse(

        @NotNull Long userId,
        @NotNull MealType mealType

) {
}
