package com.github.tennyros.makeithealthy.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Goal;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "User profile information response")
public record UserResponseDto(

        @Schema(description = "Unique user identifier", example = "456")
        Long id,

        @Schema(description = "Full name", example = "Emma Johnson")
        String name,

        @Schema(description = "Email address", example = "emma@example.com")
        String email,

        @Schema(description = "Age in years", example = "28")
        int age,

        @Schema(description = "Biological sex", allowableValues = {"MALE", "FEMALE", "OTHER"})
        Gender gender,

        @Schema(description = "Calculated daily calorie norm", example = "1850.0")
        double dailyCalorieNorm,

        @Schema(description = "Fitness goal",
                allowableValues = {"WEIGHT_LOSS", "MAINTENANCE", "MUSCLE_GAIN"})
        Goal goal,

        @Schema(description = "Account creation date", example = "2025-04-05")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate createdAt
) {
}
