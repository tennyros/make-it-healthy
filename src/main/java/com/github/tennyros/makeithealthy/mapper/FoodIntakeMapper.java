package com.github.tennyros.makeithealthy.mapper;

import com.github.tennyros.makeithealthy.dto.request.FoodIntakeRequest;
import com.github.tennyros.makeithealthy.dto.response.FoodIntakeResponse;
import com.github.tennyros.makeithealthy.entity.FoodIntake;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodIntakeMapper {

    FoodIntake toEntity(FoodIntakeRequest request);

    FoodIntakeRequest toRequest(FoodIntake entity);

    FoodIntake toEntity(FoodIntakeResponse response);

    FoodIntakeResponse toResponse(FoodIntake entity);

}
