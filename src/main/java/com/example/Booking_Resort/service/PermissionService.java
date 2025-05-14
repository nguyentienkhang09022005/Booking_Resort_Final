package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.PermissionRequest;
import com.example.Booking_Resort.dto.response.PermissionRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.PermissionMapper;
import com.example.Booking_Resort.models.Permissions;
import com.example.Booking_Resort.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService
{
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    // Hàm tạo permission cho role
    public PermissionRespone createPermission(PermissionRequest request)
    {
        if (permissionRepository.findById(request.getName()).isPresent())
        {
            throw new ApiException(ErrorCode.PERMISSIONS_EXISTS);
        }
        Permissions permissions = permissionMapper.toPermissions(request);
        permissions = permissionRepository.save(permissions);
        return permissionMapper.toPermissionRespone(permissions);
    }

    // Hàm trả về danh sách các permission
    public List<PermissionRespone> getAllPermission()
    {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionRespone).toList();
    }

    // Hàm xóa permission qua name
    public void deletePermission(String permissionName)
    {
        permissionRepository.findById(permissionName).orElseThrow(
                () -> new ApiException(ErrorCode.PERMISSIONS_NOT_FOUND)
        );
        permissionRepository.deleteById(permissionName);
    }
}
