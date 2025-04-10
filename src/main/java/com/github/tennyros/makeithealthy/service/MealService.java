package com.github.tennyros.makeithealthy.service;

import com.github.tennyros.makeithealthy.dto.request.MealRequest;
import com.github.tennyros.makeithealthy.dto.response.MealResponse;
import com.github.tennyros.makeithealthy.exception.MealAlreadyExistException;
import com.github.tennyros.makeithealthy.mapper.MealMapper;
import com.github.tennyros.makeithealthy.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepo;
    private final MealMapper mapper;

    @Transactional(readOnly = true)
    public Optional<MealResponse> getMeal(Long id) {
        return mealRepo.findById(id)
                .map(mapper::toResponse);
    }

    public MealResponse createMeal(MealRequest mealRequest) {
        if (mealRepo.existsByName(mealRequest.name())) {
            throw new MealAlreadyExistException("Meal already exists");
        }
        return mapper.toResponse(mealRepo.save(mapper.toEntity(mealRequest)));
    }

}
