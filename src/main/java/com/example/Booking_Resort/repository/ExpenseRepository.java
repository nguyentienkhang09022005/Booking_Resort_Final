package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findByIdResort_IdRs(String idResort);
}