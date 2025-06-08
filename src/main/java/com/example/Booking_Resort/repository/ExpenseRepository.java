package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findByIdResort_IdRs(String idResort);
    Expense findByIdResort_IdRsAndAmountAndCreateDate(String idResort, BigDecimal amount, LocalDateTime createDate);
}