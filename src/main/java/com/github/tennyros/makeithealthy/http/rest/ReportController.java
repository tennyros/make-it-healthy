package com.github.tennyros.makeithealthy.http.rest;

import com.github.tennyros.makeithealthy.dto.response.reporting.DailyReport;
import com.github.tennyros.makeithealthy.dto.response.reporting.DailySummary;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.exception.UserNotFoundException;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import com.github.tennyros.makeithealthy.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserRepository userRepository;

    @Operation(
            summary = "Generate Daily Report",
            description = "Generates a daily nutrition report for a user based on the given date",
            parameters = {
                    @Parameter(name = "userId", description = "Unique identifier of the user", required = true),
                    @Parameter(name = "date", description = "Date for the daily report", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated daily report",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyReport.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/daily")
    public DailyReport getDailyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        return reportService.generateDailyReport(user, date);
    }

    @Operation(
            summary = "Get User's Nutrition History",
            description = "Fetches a history of daily nutrition summaries for the given date range",
            parameters = {
                    @Parameter(name = "userId", description = "Unique identifier of the user", required = true),
                    @Parameter(name = "start", description = "Start date of the history range", required = true),
                    @Parameter(name = "end", description = "End date of the history range", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched nutrition history",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailySummary.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/history")
    public List<DailySummary> getNutritionHistory(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        return reportService.getHistory(user, start, end);
    }

}
