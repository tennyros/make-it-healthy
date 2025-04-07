package com.github.tennyros.makeithealthy.mapper;

import com.github.tennyros.makeithealthy.dto.request.UserRequest;
import com.github.tennyros.makeithealthy.dto.response.UserResponse;
import com.github.tennyros.makeithealthy.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserRequest dto);

    UserRequest toRequest(User user);

    User toEntity(UserResponse dto);

    UserResponse toResponse(User user);

}
