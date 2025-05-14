package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Monthly_Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyReportRepository extends JpaRepository<Monthly_Report, String> {
}