package com.github.tennyros.makeithealthy.dto.response.reporting;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Brief daily nutrition summary for history views")
public record DailySummary(

        @Schema(description = "Summary date", example = "2023-10-20")
        LocalDate date,

        @Schema(description = "Total calories consumed", example = "2100.0")
        double totalCalories,

        @Schema(description = "Goal achievement status")
        boolean isGoalAchieved
) {
}
