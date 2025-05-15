package com.example.Booking_Resort.service;

import com.example.Booking_Resort.constant.PredefinedRole;
import com.example.Booking_Resort.dto.request.RegisterRequest;
import com.example.Booking_Resort.dto.response.RegisterRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.models.Roles;
import com.example.Booking_Resort.models.User;
import com.example.Booking_Resort.repository.RoleRepository;
import com.example.Booking_Resort.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterService
{
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    // Đăng ký
    public RegisterRespone Register(RegisterRequest request)
    {
        if (!request.getPassworduser().equals(request.getConfirmpassword())) // Kiểm tra mật khẩu
        {
            throw new ApiException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        if (userRepository.existsByEmail(request.getEmail()))
        {
            throw new ApiException(ErrorCode.USER_EXISTS);
        }

        if(userRepository.existsByAccount(request.getAccount()))
        {
            throw new ApiException(ErrorCode.USER_EXISTS);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setAccount(request.getAccount());
        String encodedPassword = passwordEncoder.encode(request.getPassworduser());
        user.setPassworduser(encodedPassword);

        HashSet<Roles> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRole_user(roles);

        try {
            userRepository.save(user);

        }catch (DataIntegrityViolationException e)
        {
            throw new ApiException(ErrorCode.USER_EXISTS);
        }

        RegisterRespone registerRespone = new RegisterRespone();
        registerRespone.setEmail(user.getEmail());
        registerRespone.setAccount(user.getAccount());
        return registerRespone;
    }
}
