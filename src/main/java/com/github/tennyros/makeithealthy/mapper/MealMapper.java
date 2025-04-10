package com.github.tennyros.makeithealthy.mapper;

import com.github.tennyros.makeithealthy.dto.request.MealRequest;
import com.github.tennyros.makeithealthy.dto.response.MealResponse;
import com.github.tennyros.makeithealthy.entity.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MealMapper {

    @Mapping(target = "nutritionalInfo.calories", source = "caloriesPerSaving")
    @Mapping(target = "nutritionalInfo.proteins", source = "proteins")
    @Mapping(target = "nutritionalInfo.carbs", source = "carbs")
    @Mapping(target = "nutritionalInfo.fats", source = "fats")
    Meal toEntity(MealRequest dto);

    @Mapping(target = "nutritionalInfoDto.calories", source = "nutritionalInfo.calories")
    @Mapping(target = "nutritionalInfoDto.proteins", source = "nutritionalInfo.proteins")
    @Mapping(target = "nutritionalInfoDto.carbs", source = "nutritionalInfo.carbs")
    @Mapping(target = "nutritionalInfoDto.fats", source = "nutritionalInfo.fats")
    MealResponse toResponse(Meal meal);

}
