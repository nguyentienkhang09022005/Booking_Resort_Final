package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.response.DetailReportResponse;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.mapper.MonthlyReportMapper;
import com.example.Booking_Resort.repository.MonthlyReportRepository;
import com.example.Booking_Resort.repository.ResortRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MonthlyReportService {
    MonthlyReportRepository monthlyReportRepository;
    MonthlyReportMapper monthlyReportMapper;
    ResortRepository resortRepository;

    // Hàm lấy danh sách báo cáo
    public List<MonthlyReportResponse> listMonthlyReport() {
        return monthlyReportRepository.findAll()
                .stream()
                .map(report -> MonthlyReportResponse.builder()
                        .idReport(report.getIdReport())
                        .nameReport(report.getIdResort().getName_rs())
                        .reportMonth(report.getReportMonth())
                        .reportYear(report.getReportYear())
                        .totalRevenue(report.getTotalRevenue())
                        .totalExpense(report.getTotalExpense())
                        .netProfit(report.getNetProfit())
                        .generatedAt(report.getGeneratedAt())
                        // Map danh sách detail report
                        .details(report.getDetails().stream().map(detail ->
                                        DetailReportResponse.builder()
                                                .type(detail.getType())
                                                .amount(detail.getAmount())
                                                .createDate(detail.getCreateDate())
                                                .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}