package com.github.tennyros.makeithealthy.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Goal;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "User profile information response")
public record UserResponse(

        @Schema(description = "Unique user identifier", example = "456", requiredMode = REQUIRED)
        Long id,

        @Schema(description = "Full name", example = "Emma Johnson", requiredMode = REQUIRED)
        String name,

        @Schema(description = "Email address", example = "emma@example.com", requiredMode = REQUIRED)
        String email,

        @Schema(description = "Age in years", example = "28", requiredMode = REQUIRED)
        int age,

        @Schema(description = "Biological sex", allowableValues = {"MALE", "FEMALE", "UNSPECIFIED"}, requiredMode = REQUIRED)
        Gender gender,

        @Schema(description = "Calculated daily calorie norm", example = "1850.0", requiredMode = REQUIRED)
        double dailyCalorieNorm,

        @Schema(description = "Fitness goal",
                allowableValues = {"WEIGHT_LOSS", "MAINTENANCE", "MUSCLE_GAIN"}, requiredMode = REQUIRED)
        Goal goal

//        @Schema(description = "Account creation date", example = "2025-04-05")
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//        LocalDate createdAt
) {
}
