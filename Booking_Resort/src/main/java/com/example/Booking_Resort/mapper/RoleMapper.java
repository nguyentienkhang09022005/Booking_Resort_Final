package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.RoleRequest;
import com.example.Booking_Resort.dto.response.RoleResponse;
import com.example.Booking_Resort.models.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper
{
    @Mapping(target = "permissions", ignore = true)
    Roles toRole(RoleRequest request);
    RoleResponse toRoleRespone(Roles role);
}
