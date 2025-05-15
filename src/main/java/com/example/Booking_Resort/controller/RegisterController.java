package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.RegisterRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.RegisterRespone;
import com.example.Booking_Resort.service.RegisterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/user")
public class RegisterController {
    RegisterService registerService;

    // Endpoint đăng ký
    @PostMapping("/register")
    public ApiRespone<RegisterRespone> Register(@RequestBody RegisterRequest request)
    {
        return ApiRespone.<RegisterRespone>builder()
                .message("Successful registration")
                .data(registerService.Register(request))
                .build();
    }
}
