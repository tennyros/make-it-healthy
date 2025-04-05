package com.github.tennyros.makeithealthy.dto.response.reporting;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Detailed daily nutrition report")
public record DailyReportDto(

        @Schema(description = "Report date in YYYY-MM-DD format", example = "2023-10-20")
        LocalDate date,

        @Schema(description = "Total calories consumed", example = "1850.5")
        double totalCalories,

        @Schema(description = "User's daily calorie goal", example = "2000.0")
        double dailyNorm,

        @Schema(description = "Whether daily goal was achieved")
        boolean isGoalAchieved,

        @Schema(description = "Meal breakdown by eating time")
        List<MealTimeSummaryDto> meals
) {
}
