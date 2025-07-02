package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Detail_Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DetailMonthlyReportRepository extends JpaRepository<Detail_Report, String> {
    @Query(value = """
    SELECT 
        EXTRACT(MONTH FROM d.create_date) AS reportMonth,
        SUM(CASE WHEN d.type = 'Thu' THEN d.amount ELSE 0 END) AS totalRevenue,
        SUM(CASE WHEN d.type = 'Chi' THEN d.amount ELSE 0 END) AS totalExpense
    FROM detail_report d
    JOIN monthly_report r ON r.id_report = d.id_report
    WHERE r.id_rs = :resortId 
      AND EXTRACT(YEAR FROM d.create_date) = :reportYear
    GROUP BY reportMonth
    ORDER BY reportMonth
""", nativeQuery = true)
    List<Object[]> getMonthlyRevenueExpense(
            @Param("resortId") String resortId,
            @Param("reportYear") int reportYear
    );
}