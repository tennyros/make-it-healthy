package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.request.UserRequest;
import com.github.tennyros.makeithealthy.dto.response.UserResponse;
import com.github.tennyros.makeithealthy.entity.Gender;
import com.github.tennyros.makeithealthy.entity.Goal;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.exception.UserAlreadyExistsException;
import com.github.tennyros.makeithealthy.mapper.UserMapper;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "test@example.com";
    private static final String NAME = "John";
    private static final int AGE = 30;
    private static final double WEIGHT = 80.0;
    private static final double HEIGHT = 180.0;
    private static final double NORM = 2500.0;
    private static final Goal GOAL = Goal.MAINTENANCE;
    private static final Gender GENDER = Gender.MALE;

    private final UserRequest userRequest = new UserRequest(NAME, EMAIL,
            AGE, GENDER, WEIGHT, HEIGHT, GOAL);

    private final UserResponse userResponse = new UserResponse(
            ID, NAME, EMAIL, AGE,
            GENDER, NORM, GOAL
    );

    @Mock private UserRepository userRepo;

    @Mock private UserMapper mapper;

    @Mock private CalorieCalculationService calorieCalculationService;

    @InjectMocks private UserService userService;

    @Test
    void getUser_shouldReturnUserResponse() {
        User user = new User();

        when(userRepo.findById(ID)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(userResponse);

        Optional<UserResponse> result = userService.getUser(ID);

        assertTrue(result.isPresent());
        assertEquals(userResponse, result.get());
    }

    @Test
    void createUser_shouldThrowIfExists() {
        when(userRepo.existsByEmail(EMAIL)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userRequest));
    }

    @Test
    void createUser_shouldSaveAndReturnResponse() {
        User user = User.builder().name(NAME).email(EMAIL).age(AGE)
                .gender(GENDER).weight(WEIGHT).height(HEIGHT).goal(Goal.MAINTENANCE)
                .build();

        User savedUser = User.builder().id(ID).name(NAME).email(EMAIL).age(AGE)
                .gender(GENDER).weight(WEIGHT).height(HEIGHT).goal(Goal.MAINTENANCE).dailyCalorieNorm(NORM)
                .build();

        when(userRepo.existsByEmail(EMAIL)).thenReturn(false);
        when(mapper.toEntity(userRequest)).thenReturn(user);
        when(calorieCalculationService.calculateDailyNorm(user)).thenReturn(NORM);
        when(userRepo.save(user)).thenReturn(savedUser);
        when(mapper.toResponse(savedUser)).thenReturn(userResponse);

        UserResponse result = userService.createUser(userRequest);

        assertEquals(userResponse, result);
        verify(userRepo).save(user);
    }

}
