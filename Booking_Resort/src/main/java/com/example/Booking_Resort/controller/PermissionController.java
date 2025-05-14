package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.PermissionRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.PermissionRespone;
import com.example.Booking_Resort.service.PermissionService;
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
@RequestMapping("/api/permission")
public class PermissionController
{
    PermissionService permissionService;

    @PostMapping
    ApiRespone<PermissionRespone> createPermission(@RequestBody PermissionRequest request)
    {
        return ApiRespone.<PermissionRespone>builder()
                .data(permissionService.createPermission(request))
                .build();
    }

    @GetMapping
    ApiRespone<List<PermissionRespone>> getAllPermission()
    {
        return ApiRespone.<List<PermissionRespone>>builder()
                .data(permissionService.getAllPermission())
                .build();
    }

    @DeleteMapping("/{permissionName}")
    ApiRespone<Void> deletePermission(@PathVariable String permissionName)
    {
        permissionService.deletePermission(permissionName);
        return ApiRespone.<Void>builder().build();
    }
}
