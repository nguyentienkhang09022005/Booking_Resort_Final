package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment, String> { }
