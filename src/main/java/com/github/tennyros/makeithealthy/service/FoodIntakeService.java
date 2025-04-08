package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.request.FoodIntakeRequest;
import com.github.tennyros.makeithealthy.dto.response.FoodIntakeResponse;
import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.Meal;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.exception.MealNotFoundException;
import com.github.tennyros.makeithealthy.exception.UserNotFoundException;
import com.github.tennyros.makeithealthy.mapper.FoodIntakeMapper;
import com.github.tennyros.makeithealthy.repository.FoodIntakeRepository;
import com.github.tennyros.makeithealthy.repository.MealRepository;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodIntakeService {

    private final FoodIntakeRepository foodIntakeRepo;
    private final UserRepository userRepo;
    private final MealRepository mealRepo;
    private final FoodIntakeMapper mapper;

    @Transactional(readOnly = true)
    public Optional<FoodIntakeResponse> getFoodIntake(Long id) {
        return foodIntakeRepo.findById(id)
                .map(mapper::toResponse);
    }

    public FoodIntakeResponse createFoodIntake(FoodIntakeRequest request) {
        User user = userRepo.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + request.userId() + " not found"));

        List<Meal> meals = mealRepo.findAllById(request.mealIds());
        if (meals.size() != request.mealIds().size()) {
            throw new MealNotFoundException("Some meals not found");
        }

        double totalCalories = meals.stream()
                .mapToDouble(meal -> meal.getNutritionalInfo().getCalories())
                .sum();

        FoodIntake intake = FoodIntake.builder()
                .user(user)
                .mealType(request.mealType())
                .date(LocalDate.now())
                .totalCalories(totalCalories)
                .meals(meals)
                .build();

        FoodIntake saved = foodIntakeRepo.save(intake);
        return mapper.toResponse(saved);
    }

}
