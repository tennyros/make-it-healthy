package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.request.UserRequest;
import com.github.tennyros.makeithealthy.dto.response.UserResponse;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.exception.UserAlreadyExistsException;
import com.github.tennyros.makeithealthy.mapper.UserMapper;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper mapper;
    private final CalorieCalculationService calorieCalculationService;

    @Transactional(readOnly = true)
    public Optional<UserResponse> getUser(Long id) {
        return userRepo.findById(id)
                .map(mapper::toResponse);
    }

    public UserResponse createUser(UserRequest userRequest) {
        if (userRepo.existsByEmail(userRequest.email())) {
            throw new UserAlreadyExistsException("User with such email already exists");
        }

        User user = mapper.toEntity(userRequest);
        user.setDailyCalorieNorm(calorieCalculationService.calculateDailyNorm(user));

        return mapper.toResponse(userRepo.save(user));
    }
}
