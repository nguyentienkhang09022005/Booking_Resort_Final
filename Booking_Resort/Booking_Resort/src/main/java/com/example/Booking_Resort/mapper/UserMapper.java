package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.UserCreationRequest;
import com.example.Booking_Resort.dto.request.UserUpdateRequest;
import com.example.Booking_Resort.dto.response.UserRespone;
import com.example.Booking_Resort.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserRespone toUserRespone(User user);

    User toUser(UserCreationRequest request);

    @Mapping(target = "role_user", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
