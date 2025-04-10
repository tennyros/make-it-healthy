package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.response.reporting.DailyReport;
import com.github.tennyros.makeithealthy.dto.response.reporting.DailySummary;
import com.github.tennyros.makeithealthy.dto.response.reporting.MealTimeSummary;
import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.Meal;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.repository.FoodIntakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final FoodIntakeRepository foodIntakeRepository;
    private final CalorieCalculationService calorieCalculationService;

    public DailyReport generateDailyReport(User user, LocalDate date) {
        List<FoodIntake> intakes = foodIntakeRepository.findByUserAndDate(user, date);

        double totalCalories = calorieCalculationService.calculateTotalCalories(intakes);
        boolean isGoalAchieved = totalCalories <= user.getDailyCalorieNorm();

        return new DailyReport(
                date,
                totalCalories,
                user.getDailyCalorieNorm(),
                isGoalAchieved,
                mapToMealSummaries(intakes)
        );
    }

    public List<DailySummary> getHistory(User user, LocalDate start, LocalDate end) {
        return foodIntakeRepository
                .findByUserAndDateBetween(user, start, end)
                .stream()
                .collect(Collectors.groupingBy(
                        FoodIntake::getDate,
                        Collectors.summarizingDouble(FoodIntake::getTotalCalories)
                ))
                .entrySet().stream()
                .map(e -> new DailySummary(
                        e.getKey(),
                        e.getValue().getSum(),
                        e.getValue().getSum() <= user.getDailyCalorieNorm()
                ))
                .toList();
    }

    private List<MealTimeSummary> mapToMealSummaries(List<FoodIntake> intakes) {
        return intakes.stream()
                .map(intake -> new MealTimeSummary(
                        intake.getMealType(),
                        intake.getMeals().stream()
                                .mapToDouble(m -> m.getNutritionalInfo().getCalories())
                                .sum(),
                        intake.getMeals().stream()
                                .map(Meal::getName)
                                .toList()
                ))
                .toList();
    }

}