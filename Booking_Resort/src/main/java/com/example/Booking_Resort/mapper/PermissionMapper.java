package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.PermissionRequest;
import com.example.Booking_Resort.dto.response.PermissionRespone;
import com.example.Booking_Resort.models.Permissions;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper
{
    Permissions toPermissions(PermissionRequest request);
    PermissionRespone toPermissionRespone(Permissions permissions);
}
