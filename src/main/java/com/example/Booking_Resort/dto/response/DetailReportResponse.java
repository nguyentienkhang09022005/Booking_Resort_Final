package com.example.Booking_Resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailReportResponse {
    private String type;
    private String titleOfExpense;
    private String titleOfIncome;
    private BigDecimal amount;
    private LocalDateTime createDate;
}
