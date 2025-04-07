package com.github.tennyros.makeithealthy.dto.request;

import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for user registration")
public record UserRequest(

        @Schema(description = "Full name of the user", example = "John Doe", minLength = 2, maxLength = 64)
        @NotBlank(message = "Name must be defined") @Size(min = 2, max = 64, message = "Name is invalid")
        String name,

        @Schema(description = "Valid email address", example = "user@example.com")
        @NotBlank @Email(message = "Email must be valid") @Size(max = 255, message = "Email is too long")
        String email,

        @Schema(description = "Age in years (14-115)", example = "30")
        @NotNull(message = "Age must be defined")
        @Min(value = 14, message = "Age must be valid")
        @Max(value = 115, message = "Age must be valid")
        int age,

        @Schema(description = "Biological sex for BMR calculation",
                allowableValues = {"MALE", "FEMALE", "OTHER"})
        @NotNull(message = "Gender must be specified")
        Gender gender,

        @Schema(description = "Weight in kilograms (10-400kg)", example = "75.5")
        @DecimalMin(value = "10.0", message = "Weight must have valid number")
        @DecimalMax(value = "400.0", message = "Weight must have valid number")
        double weight,

        @Schema(description = "Height in centimeters (40-220cm)", example = "175.0")
        @DecimalMin(value = "40.0", message = "Weight must have valid number")
        @DecimalMax(value = "220.0", message = "Weight must have valid number")
        double height,

        @Schema(description = "Fitness goal type",
                allowableValues = {"WEIGHT_LOSS", "MAINTENANCE", "MUSCLE_GAIN"})
        @NotNull(message = "Goal must be specified")
        Goal goal
) {

}
