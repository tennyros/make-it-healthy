package com.github.tennyros.makeithealthy.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "Meal information response")
public record MealResponse(

        @Schema(description = "Unique meal identifier", example = "123", requiredMode = REQUIRED)
        Long id,

        @Schema(description = "Meal name", example = "Avocado Toast", requiredMode = REQUIRED)
        String name,

        @Schema(description = "Nutritional information", requiredMode = REQUIRED)
        NutritionalInfoDto nutritionalInfoDto
) {
}
