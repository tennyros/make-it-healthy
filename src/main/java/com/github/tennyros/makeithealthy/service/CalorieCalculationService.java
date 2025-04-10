package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CalorieCalculationService {

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

    private double calculateBMR(User user) {
        double bmr = user.getGender() == Gender.MALE
                ? (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge()) + 88.362
                : (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge()) + 447.593;

        return BigDecimal.valueOf(bmr)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
