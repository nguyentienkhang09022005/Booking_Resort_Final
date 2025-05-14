package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.AuthenticationRequest;
import com.example.Booking_Resort.dto.request.IntrospectRequest;
import com.example.Booking_Resort.dto.request.LogoutRequest;
import com.example.Booking_Resort.dto.request.RefreshRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.AuthenticationRespone;
import com.example.Booking_Resort.dto.response.IntrospectResponse;
import com.example.Booking_Resort.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController
{
    final AuthenticationService authenticationService;

    // Endpoint đăng nhập tạo TOKEN
    @PostMapping("/login")
    public ApiRespone<AuthenticationRespone> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticated(request);
        return ApiRespone.<AuthenticationRespone>builder()
                .data(result)
                .build();
    }

    // Endpoint đăng nhập với TOKEN
    @PostMapping("/introspect")
    public ApiRespone<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiRespone.<IntrospectResponse>builder()
                .data(result)
                .build();
    }

    // Endpoint đăng xuất
    @PostMapping("/logout")
    public ApiRespone<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.Logout(request);
        return ApiRespone.<Void>builder()
                .build();
    }

    // Endpoint refresh TOKEN
    @PostMapping("/refresh")
    public ApiRespone<AuthenticationRespone> authenticate(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationService.reFreshToken(request);
        return ApiRespone.<AuthenticationRespone>builder()
                .data(result)
                .build();
    }
}
