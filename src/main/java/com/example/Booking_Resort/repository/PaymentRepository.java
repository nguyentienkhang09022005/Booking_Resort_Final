package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment, String> {
    List<Payment> findByIdBr_IdBrIn(List<String> idBrList);
}
