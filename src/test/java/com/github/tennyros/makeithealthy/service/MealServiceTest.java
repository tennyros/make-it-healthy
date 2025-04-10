package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.request.MealRequest;
import com.github.tennyros.makeithealthy.dto.response.MealResponse;
import com.github.tennyros.makeithealthy.dto.response.NutritionalInfoDto;
import com.github.tennyros.makeithealthy.entity.Meal;
import com.github.tennyros.makeithealthy.exception.MealAlreadyExistException;
import com.github.tennyros.makeithealthy.mapper.MealMapper;
import com.github.tennyros.makeithealthy.repository.MealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    private static final Long ID = 1L;
    private static final String MEAL_NAME = "Avocado Toast";
    private static final double CALORIES = 100;
    private static final double PROTEINS = 10;
    private static final double CARBS = 10;
    private static final double FATS = 10;

    private final MealRequest request = new MealRequest(
            MEAL_NAME, CALORIES, PROTEINS, CARBS, FATS);

    private final MealResponse response = new MealResponse(
            ID, MEAL_NAME, new NutritionalInfoDto(CALORIES, PROTEINS, CARBS, FATS));

    @Mock private MealRepository mealRepo;

    @Mock private MealMapper mapper;

    @InjectMocks private MealService service;

    @Test
    void getMeal_shouldReturnMealResponse() {
        Meal meal = new Meal();


        when(mealRepo.findById(ID)).thenReturn(Optional.of(meal));
        when(mapper.toResponse(meal)).thenReturn(response);

        Optional<MealResponse> result = service.getMeal(ID);

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
    }

    @Test
    void createMeal_shouldThrowIfExists() {
        when(mealRepo.existsByName(MEAL_NAME)).thenReturn(true);

        assertThrows(MealAlreadyExistException.class, () -> service.createMeal(request));
    }

    @Test
    void createMeal_shouldSaveAndReturnResponse() {
        Meal meal = new Meal();

        when(mealRepo.existsByName(MEAL_NAME)).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(meal);
        when(mealRepo.save(meal)).thenReturn(meal);
        when(mapper.toResponse(meal)).thenReturn(response);

        MealResponse result = service.createMeal(request);

        assertEquals(response, result);
    }

}
