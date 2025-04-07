package com.github.tennyros.makeithealthy.dto.request;

import com.github.tennyros.makeithealthy.entity.MealType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record FoodInTakeRequest(

        @NotNull Long userId,
        @NotNull MealType mealType,
//        @NotNull LocalDateTime intakeTime,
        @NotEmpty List<Long> mealIds

) {
}
