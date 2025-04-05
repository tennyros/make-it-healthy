package com.github.tennyros.makeithealthy.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed nutritional values")
public record NutritionalInfoDto(

        @Schema(description = "Calories per serving (kcal)", example = "250.0")
        double calories,

        @Schema(description = "Proteins per serving (g)", example = "10.5")
        double proteins,

        @Schema(description = "Carbohydrates per serving (g)", example = "30.2")
        double carbs,

        @Schema(description = "Fats per serving (g)", example = "12.3")
        double fats
) {

}
