package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.MonthlyReportGetInfomationRequest;
import com.example.Booking_Resort.dto.response.DetailReportResponse;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.models.Monthly_Report;
import com.example.Booking_Resort.repository.MonthlyReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MonthlyReportService {
    MonthlyReportRepository monthlyReportRepository;

    // Hàm chi tiết thông tin báo cáo của tháng/năm
    public MonthlyReportResponse infMonthReport(MonthlyReportGetInfomationRequest request) {

        Monthly_Report report = monthlyReportRepository
                .findByIdResort_IdRsAndReportYearAndReportMonth(
                        request.getIdResort(),
                        request.getReportYear(),
                        request.getReportMonth())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorCode.REPORT_NOT_FOUND));

        return MonthlyReportResponse.builder()
                .idReport(report.getIdReport())
                .nameReport(report.getIdResort().getName_rs())
                .reportMonth(report.getReportMonth())
                .reportYear(report.getReportYear())
                .totalRevenue(report.getTotalRevenue())
                .totalExpense(report.getTotalExpense())
                .netProfit(report.getNetProfit())
                .generatedAt(report.getGeneratedAt())
                .details(report.getDetails().stream().map(detail ->
                                DetailReportResponse.builder()
                                        .type(detail.getType())
                                        .amount(detail.getAmount())
                                        .createDate(detail.getCreateDate())
                                        .build())
                        .collect(Collectors.toList()))
                .build();
    }
}