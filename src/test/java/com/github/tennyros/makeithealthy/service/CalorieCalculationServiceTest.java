package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Goal;
import com.github.tennyros.makeithealthy.entity.NutritionalInfo;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.entity.Meal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalorieCalculationServiceTest {

    private CalorieCalculationService service;

    @BeforeEach
    void setUp() {
        service = new CalorieCalculationService();
    }

    @Test
    void calculateMaleDailyNorm_shouldReturnAdjustedCalories() {
        User user = User.builder()
                .gender(Gender.MALE)
                .age(25)
                .weight(75.0)
                .height(180.0)
                .goal(Goal.MAINTENANCE)
                .build();
        double result = service.calculateDailyNorm(user);

        // BMR для MALE с 75 кг, 180 см, 25 лет:
        // (13.397 * 75) + (4.799 * 180) - (5.677 * 25) + 88.362 = 1815.032
        assertEquals(1815.03, result, 0.01);
    }

    @Test
    void calculateFemaleDailyNorm_shouldReturnAdjustedCalories() {
        User user = User.builder()
                .gender(Gender.FEMALE)
                .age(25)
                .weight(50.0)
                .height(165.0)
                .goal(Goal.MAINTENANCE)
                .build();
        double result = service.calculateDailyNorm(user);

        // BMR для FEMALE с 50 кг, 165 см, 25 лет:
        // (9.247 * 50) + (3.098 * 165) - (4.330 * 25) + 447.593 = 1312.863
        assertEquals(1312.86, result, 0.01);
    }

    @Test
    void calculateTotalCalories_shouldReturnSumOfMealCalories() {
        Meal m1 = new Meal();
        m1.setNutritionalInfo(new NutritionalInfo(100, 0, 0, 0));

        Meal m2 = new Meal();
        m2.setNutritionalInfo(new NutritionalInfo(200, 0, 0, 0));

        FoodIntake intake = new FoodIntake();
        intake.setMeals(List.of(m1, m2));

        double result = service.calculateTotalCalories(List.of(intake));
        assertEquals(300.0, result);
    }

}