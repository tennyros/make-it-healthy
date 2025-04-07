package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalorieCalculationService {

    public double calculateDailyNorm(User user) {
        double bmr = calculateBMR(user);
        return user.getGoal().calculateAdjustedCalories(bmr);
    }

    private double calculateBMR(User user) {
        return user.getGender() == Gender.MALE
                ? (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge()) + 88.362
                : (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge()) + 447.593;
    }
}
