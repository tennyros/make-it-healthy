package com.github.tennyros.makeithealthy.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Meal information response")
public record MealResponse(

        @Schema(description = "Unique meal identifier", example = "123")
        Long id,

        @Schema(description = "Meal name", example = "Avocado Toast")
        String name,

        @Schema(description = "Nutritional information")
        NutritionalInfoDto nutritionalInfoDto
) {
}
