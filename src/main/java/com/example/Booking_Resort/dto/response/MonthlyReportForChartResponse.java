package com.example.Booking_Resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportForChartResponse {
    private String idReport;
    private Integer reportMonth;
    private Integer reportYear;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal netProfit;
}
