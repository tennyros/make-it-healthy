package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.request.FoodIntakeRequest;
import com.github.tennyros.makeithealthy.dto.response.FoodIntakeResponse;
import com.github.tennyros.makeithealthy.entity.FoodIntake;
import com.github.tennyros.makeithealthy.entity.Meal;
import com.github.tennyros.makeithealthy.entity.MealType;
import com.github.tennyros.makeithealthy.entity.NutritionalInfo;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.exception.MealNotFoundException;
import com.github.tennyros.makeithealthy.exception.UserNotFoundException;
import com.github.tennyros.makeithealthy.mapper.FoodIntakeMapper;
import com.github.tennyros.makeithealthy.repository.FoodIntakeRepository;
import com.github.tennyros.makeithealthy.repository.MealRepository;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodIntakeServiceTest {

    private static final Long ID = 1L;
    private static final List<Long> MEALS = List.of(100L, 101L);

    private final FoodIntakeResponse response = new FoodIntakeResponse(ID, MealType.LUNCH);
    private final FoodIntakeRequest request = new FoodIntakeRequest(ID, MealType.LUNCH, MEALS);
    private final User user = new User();
    private final Meal meal1 = new Meal();

    @Mock private FoodIntakeRepository foodIntakeRepo;

    @Mock private UserRepository userRepo;

    @Mock private MealRepository mealRepo;

    @Mock private FoodIntakeMapper mapper;

    @InjectMocks private FoodIntakeService foodIntakeService;

    @Test
    void getFoodIntake_shouldReturnResponse() {
        FoodIntake intake = new FoodIntake();

        when(foodIntakeRepo.findById(ID)).thenReturn(Optional.of(intake));
        when(mapper.toResponse(intake)).thenReturn(response);

        Optional<FoodIntakeResponse> result = foodIntakeService.getFoodIntake(ID);

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
    }

    @Test
    void createFoodIntake_shouldCreateSuccessfully() {
        Meal meal2 = new Meal();

        NutritionalInfo info1 = new NutritionalInfo();
        info1.setCalories(100.0);
        NutritionalInfo info2 = new NutritionalInfo();
        info2.setCalories(200.0);

        meal1.setNutritionalInfo(info1);
        meal2.setNutritionalInfo(info2);

        FoodIntake saved = FoodIntake.builder()
                .user(user)
                .meals(List.of(meal1, meal2))
                .mealType(MealType.LUNCH)
                .totalCalories(300.0)
                .date(LocalDate.now())
                .build();

        when(userRepo.findById(ID)).thenReturn(Optional.of(user));
        when(mealRepo.findAllById(MEALS)).thenReturn(List.of(meal1, meal2));
        when(foodIntakeRepo.save(any(FoodIntake.class))).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        FoodIntakeResponse result = foodIntakeService.createFoodIntake(request);

        assertEquals(response, result);
        verify(foodIntakeRepo).save(any(FoodIntake.class));
    }

    @Test
    void createFoodIntake_shouldThrowIfUserNotFound() {
        when(userRepo.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> foodIntakeService.createFoodIntake(request));
    }

    @Test
    void createFoodIntake_shouldThrowIfSomeMealsNotFound() {
        when(userRepo.findById(ID)).thenReturn(Optional.of(user));
        when(mealRepo.findAllById(MEALS)).thenReturn(List.of(meal1));

        assertThrows(MealNotFoundException.class,
                () -> foodIntakeService.createFoodIntake(request));
    }

}
