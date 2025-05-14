package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.MainBody;
import com.example.Booking_Resort.dto.request.ChangePasswordRequest;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.models.Otp;
import com.example.Booking_Resort.models.User;
import com.example.Booking_Resort.repository.ForgotPasswordRepository;
import com.example.Booking_Resort.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ForgotPasswordService
{
    UserRepository userRepository;
    EmailService emailService;
    ForgotPasswordRepository forgotPasswordRepository;
    PasswordEncoder passwordEncoder;

    // Hàm xác thực email để tạo OTP
    public void verifyEmail(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_EXIST)
        );

        int otp = otpGenerator();
        MainBody mainBody = MainBody.builder()
                .to(user.getEmail())
                .subject("OTP for forgot password request")
                .text("Mã OTP của bạn là: " + otp)
                .build();

        Otp saveOtp = Otp.builder()
                .otp(otp)
                .expiration_time(LocalDateTime.now().plusMinutes(2))// OTP có hiệu lực trong 2 phút
                .user(user)
                .build();

        emailService.sendOtpEmail(mainBody);
        forgotPasswordRepository.save(saveOtp);
    }

    // Hàm xác thực OTP
    public ResponseEntity<String> verifyOtp(Integer otp, String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );
        Otp checkOtp = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(
                () -> new ApiException(ErrorCode.OTP_NOT_EXIST_FOR_EMAIL)
        );

        if (checkOtp.getExpiration_time().isBefore(LocalDateTime.now()))
        {
            forgotPasswordRepository.deleteById(checkOtp.getId_otp());
            return new ResponseEntity<>("OTP not exist for email", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified!");
    }

    // Hàm thay đổi mật khẩu
    public ResponseEntity<String> changePassword(ChangePasswordRequest request, String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );

        if (!request.getPassword().equals(request.getRepeatPassword()))
        {
            return new ResponseEntity<>("Enter the password again", HttpStatus.EXPECTATION_FAILED);
        }
        String newPassword = passwordEncoder.encode(request.getPassword());
        user.setPassworduser(newPassword);
        userRepository.save(user);
        return ResponseEntity.ok("Password changed!");
    }

    // Hàm tạo OTP
    private Integer otpGenerator()
    {
        Random random = new Random();
        return random.nextInt(100000,1000000);
    }

    // Hàm Resend OTP
    public void resendOTP(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );

        forgotPasswordRepository.findByUser(user).ifPresent(existingOtp -> {
            forgotPasswordRepository.delete(existingOtp);
        });
        int otp = otpGenerator();
        MainBody mainBody = MainBody.builder()
                .to(user.getEmail())
                .subject("OTP for forgot password request")
                .text("Mã OTP của bạn là: " + otp)
                .build();

        Otp saveOtp = Otp.builder()
                .otp(otp)
                .expiration_time(LocalDateTime.now().plusMinutes(2))// OTP có hiệu lực trong 2 phút
                .user(user)
                .build();

        emailService.sendOtpEmail(mainBody);
        forgotPasswordRepository.save(saveOtp);
    }
}