package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Monthly_Report;
import com.example.Booking_Resort.models.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface MonthlyReportRepository extends JpaRepository<Monthly_Report, String> {
    Optional<Monthly_Report> findByReportMonthAndReportYearAndIdResort(int reportMonth, int reportYear, Resort idResort);
}