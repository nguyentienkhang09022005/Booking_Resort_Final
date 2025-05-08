package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.ChangePasswordRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.service.ForgotPasswordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    ForgotPasswordService forgotPasswordService;

    // Endpoint tạo OTP từ mail
    @PostMapping("/verifyEmail/{email}")
    public ApiRespone<Void> verifyEmail(@PathVariable String email) {
        forgotPasswordService.verifyEmail(email);
        return ApiRespone.<Void>builder()
                .message("Đã gửi mã OTP về email của bạn.")
                .build();
    }

    // Endpoint xác thực OTP
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ApiRespone<ResponseEntity<String>> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        forgotPasswordService.verifyOtp(otp, email);
        return ApiRespone.<ResponseEntity<String>>builder()
                .data(forgotPasswordService.verifyOtp(otp, email))
                .build();
    }

    // Endpoint thay đổi mật khẩu
    @PostMapping("/changePassword/{email}")
    public ApiRespone<ResponseEntity<String>> changePassword(@RequestBody ChangePasswordRequest request,
                                                             @PathVariable String email) {
        return ApiRespone.<ResponseEntity<String>>builder()
                .data(forgotPasswordService.changePassword(request, email))
                .build();
    }
}

