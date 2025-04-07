package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.request.UserRequest;
import com.github.tennyros.makeithealthy.dto.response.UserResponse;
import com.github.tennyros.makeithealthy.entity.User;
import com.github.tennyros.makeithealthy.exception.EmailAlreadyExistsException;
import com.github.tennyros.makeithealthy.mapper.UserMapper;
import com.github.tennyros.makeithealthy.repository.UserRepository;
import jakarta.validation.Valid;
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
    public Optional<UserResponse> getUserById(Long id) {
        return userRepo.findById(id)
                .map(mapper::toResponse);
    }

    public UserResponse create(@Valid UserRequest userRequest) {
        if (userRepo.existsByEmail(userRequest.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = mapper.toEntity(userRequest);
        user.setDailyCalorieNorm(calorieCalculationService.calculateDailyNorm(user));

        return mapper.toResponse(userRepo.save(user));
    }
}
