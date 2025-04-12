package com.github.tennyros.makeithealthy.controller;

import com.github.tennyros.makeithealthy.dto.response.reporting.DailyReport;
import com.github.tennyros.makeithealthy.dto.response.reporting.DailySummary;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Goal;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.http.rest.ReportController;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import com.github.tennyros.makeithealthy.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    private static final Long USER_ID = 1L;
    private static final LocalDate START = LocalDate.of(2024, 4, 1);
    private static final LocalDate END = LocalDate.of(2024, 4, 10);

    private final User user = User.builder().id(USER_ID).name("John Doe").email("johndoe@gmail.com").age(30)
            .gender(Gender.MALE).weight(75.0).height(175.0).goal(Goal.MAINTENANCE).dailyCalorieNorm(2500.0)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportService reportService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void getDailyReport_shouldReturnReport() throws Exception {
        DailyReport report = reportService.generateDailyReport(user, START);

        given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
        given(reportService.generateDailyReport(user, START)).willReturn(report);

        mockMvc.perform(get("/api/v1/reports/daily")
                        .param("userId", USER_ID.toString())
                        .param("date", START.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getDailyReport_shouldThrowIfUserNotFound() throws Exception {
        given(userRepository.findById(USER_ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/reports/daily")
                        .param("userId", USER_ID.toString())
                        .param("date", START.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNutritionHistory_shouldReturnList() throws Exception {
        List<DailySummary> summaries = reportService.getHistory(user, START, END);

        given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
        given(reportService.getHistory(user, START, END)).willReturn(summaries);

        mockMvc.perform(get("/api/v1/reports/history")
                        .param("userId", USER_ID.toString())
                        .param("start", START.toString())
                        .param("end", END.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getNutritionHistory_shouldThrowIfUserNotFound() throws Exception {
        given(userRepository.findById(USER_ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/reports/history")
                        .param("userId", USER_ID.toString())
                        .param("date", START.toString())
                        .param("start", END.toString())
                        .param("end", END.toString()))
                .andExpect(status().isNotFound());
    }

}

