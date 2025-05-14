package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.RoleRequest;
import com.example.Booking_Resort.dto.response.RoleResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.RoleMapper;
import com.example.Booking_Resort.repository.PermissionRepository;
import com.example.Booking_Resort.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService
{
    RoleMapper roleMapper;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    // Hàm tạo role
    public RoleResponse createRole(RoleRequest request)
    {
        if(roleRepository.findById(request.getName()).isPresent())
        {
            throw new ApiException(ErrorCode.ROLE_EXISTS);
        }
        var role = roleMapper.toRole(request);
        var permission = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permission));
        role = roleRepository.save(role);

        return roleMapper.toRoleRespone(role);
    }

    // Hàm lấy danh sách role
    public List<RoleResponse> getAllRole()
    {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleRespone).toList();
    }

    // Hàm xóa role
    public void deleteRole(String roleName)
    {
        roleRepository.findById(roleName).orElseThrow(
                () -> new ApiException(ErrorCode.ROLES_NOT_FOUND)
        );
        roleRepository.deleteById(roleName);
    }
}
