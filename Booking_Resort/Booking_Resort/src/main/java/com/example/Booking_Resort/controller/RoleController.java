package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.RoleRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.RoleResponse;
import com.example.Booking_Resort.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/role")
public class RoleController
{
    RoleService roleService;

    @PostMapping
    ApiRespone<RoleResponse> createRole(@RequestBody RoleRequest request)
    {
        return ApiRespone.<RoleResponse>builder()
                .data(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiRespone<List<RoleResponse>> getAllRoles()
    {
        return ApiRespone.<List<RoleResponse>>builder()
                .data(roleService.getAllRole())
                .build();
    }

    @DeleteMapping("/{roleName}")
    ApiRespone<Void> deleteRole(@PathVariable String roleName)
    {
        roleService.deleteRole(roleName);
        return ApiRespone.<Void>builder().build();
    }
}
