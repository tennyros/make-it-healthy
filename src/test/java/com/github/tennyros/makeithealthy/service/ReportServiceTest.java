package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.response.reporting.DailyReport;
import com.github.tennyros.makeithealthy.dto.response.reporting.DailySummary;
import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.Meal;
import com.github.tennyros.makeithealthy.entity.MealType;
import com.github.tennyros.makeithealthy.entity.NutritionalInfo;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.repository.FoodIntakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock private FoodIntakeRepository foodIntakeRepository;

    @Mock private CalorieCalculationService calorieCalculationService;

    @InjectMocks private ReportService reportService;

    @Test
    void generateDailyReport_shouldReturnCorrectReport() {
        User user = new User();
        user.setDailyCalorieNorm(2000.0);

        LocalDate date = LocalDate.now();

        Meal meal = new Meal();
        meal.setName("Salad");
        meal.setNutritionalInfo(new NutritionalInfo(300, 0, 0, 0));

        FoodIntake intake = new FoodIntake();
        intake.setDate(date);
        intake.setMealType(MealType.BREAKFAST);
        intake.setMeals(List.of(meal));

        List<FoodIntake> intakes = List.of(intake);

        when(foodIntakeRepository.findByUserAndDate(user, date)).thenReturn(intakes);
        when(calorieCalculationService.calculateTotalCalories(intakes)).thenReturn(300.0);

        DailyReport report = reportService.generateDailyReport(user, date);

        assertEquals(300.0, report.totalCalories());
        assertEquals(user.getDailyCalorieNorm(), report.dailyNorm());
        assertTrue(report.isGoalAchieved());
        assertEquals(1, report.meals().size());
    }

    @Test
    void generateDailyReport_shouldReturnNotAchievedWhenCaloriesExceeded() {
        User user = new User();
        user.setDailyCalorieNorm(2000.0);

        LocalDate date = LocalDate.now();

        Meal meal = new Meal();
        meal.setName("Burger");
        meal.setNutritionalInfo(new NutritionalInfo(2500, 0, 0, 0));

        FoodIntake intake = new FoodIntake();
        intake.setDate(date);
        intake.setMealType(MealType.DINNER);
        intake.setMeals(List.of(meal));

        List<FoodIntake> intakes = List.of(intake);

        when(foodIntakeRepository.findByUserAndDate(user, date)).thenReturn(intakes);
        when(calorieCalculationService.calculateTotalCalories(intakes)).thenReturn(2500.0);

        DailyReport report = reportService.generateDailyReport(user, date);

        assertFalse(report.isGoalAchieved());
    }


    @Test
    void getHistory_shouldReturnSummarizedList() {
        User user = new User();
        user.setDailyCalorieNorm(2000.0);

        LocalDate date = LocalDate.of(2025, 4, 10);

        FoodIntake intake1 = new FoodIntake();
        intake1.setDate(date);
        intake1.setTotalCalories(400.0);

        FoodIntake intake2 = new FoodIntake();
        intake2.setDate(date);
        intake2.setTotalCalories(600.0);

        when(foodIntakeRepository.findByUserAndDateBetween(user, date, date))
                .thenReturn(List.of(intake1, intake2));

        List<DailySummary> summaries = reportService.getHistory(user, date, date);

        assertEquals(1, summaries.size());
        assertEquals(1000.0, summaries.get(0).totalCalories());
        assertTrue(summaries.get(0).isGoalAchieved());
    }

    @Test
    void getHistory_shouldMarkGoalNotAchievedIfExceeded() {
        User user = new User();
        user.setDailyCalorieNorm(1000.0);

        LocalDate date = LocalDate.of(2025, 4, 10);

        FoodIntake intake1 = new FoodIntake();
        intake1.setDate(date);
        intake1.setTotalCalories(800.0);

        FoodIntake intake2 = new FoodIntake();
        intake2.setDate(date);
        intake2.setTotalCalories(500.0);

        when(foodIntakeRepository.findByUserAndDateBetween(user, date, date))
                .thenReturn(List.of(intake1, intake2));

        List<DailySummary> summaries = reportService.getHistory(user, date, date);

        assertEquals(1, summaries.size());
        assertEquals(1300.0, summaries.get(0).totalCalories());
        assertFalse(summaries.get(0).isGoalAchieved());
    }

}
