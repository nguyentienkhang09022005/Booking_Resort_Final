package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Otp;
import com.example.Booking_Resort.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<Otp, String> {
    Optional<Otp> findByOtpAndUser(Integer otp, User user);
}
