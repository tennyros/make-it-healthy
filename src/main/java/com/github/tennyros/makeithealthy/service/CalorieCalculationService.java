package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Meal;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalorieCalculationService {

    private final MealRepository mealRepository;

    public double calculateDailyNorm(User user) {
        double bmr = calculateBMR(user);
        return user.getGoal().calculateAdjustedCalories(bmr);
    }

    public double calculateTotalCalories(List<FoodIntake> intakes) {
        return intakes.stream()
                .flatMap(intake -> intake.getMeals().stream())
                .mapToDouble(meal -> meal.getNutritionalInfo().getCalories())
                .sum();
    }

    // Альтернативная версия с оптимизированным запросом
    @Transactional
    public double calculateTotalCaloriesOptimized(List<FoodIntake> intakes) {
        List<Integer> mealIds = intakes.stream()
                .flatMap(intake -> intake.getMeals().stream())
                .map(Meal::getId)
                .toList();

        return mealRepository.sumCaloriesByIds(mealIds);
    }

    private double calculateBMR(User user) {
        return user.getGender() == Gender.MALE
                ? (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge()) + 88.362
                : (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge()) + 447.593;
    }
}
