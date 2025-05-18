package com.example.Booking_Resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportResponse {
    private String idReport;
    private String nameReport;
    private Integer reportMonth;
    private Integer reportYear;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal netProfit;
    private LocalDateTime generatedAt;
    private List<DetailReportResponse> details;
}
