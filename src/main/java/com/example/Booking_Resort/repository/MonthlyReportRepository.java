package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Monthly_Report;
import com.example.Booking_Resort.models.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MonthlyReportRepository extends JpaRepository<Monthly_Report, String> {
    Optional<Monthly_Report> findByReportMonthAndReportYearAndIdResort(int reportMonth, int reportYear, Resort idResort);

    Optional<Monthly_Report> findByIdResort_IdRsAndReportYearAndReportMonth(String idResort, int year, int month);

    List<Monthly_Report> findByIdResort_IdRsAndReportYear(String idResort, Integer reportYear);

    boolean existsByIdResort_IdRs(String idResort);
}