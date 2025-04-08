package com.github.tennyros.makeithealthy.http.rest;

import com.github.tennyros.makeithealthy.dto.response.reporting.DailyReport;
import com.github.tennyros.makeithealthy.dto.response.reporting.DailySummary;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.exception.UserNotFoundException;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import com.github.tennyros.makeithealthy.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/daily")
    public DailyReport getDailyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        return reportService.generateDailyReport(user, date);
    }

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
