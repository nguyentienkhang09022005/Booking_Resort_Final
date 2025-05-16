package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.MonthlyReportCreationRequest;
import com.example.Booking_Resort.dto.request.MonthlyReportUpdateRequest;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.MonthlyReportMapper;
import com.example.Booking_Resort.models.Detail_Report;
import com.example.Booking_Resort.models.Monthly_Report;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.repository.MonthlyReportRepository;
import com.example.Booking_Resort.repository.ResortRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                        .build())
                .collect(Collectors.toList());
    }

    // Hàm lưu báo cáo vào csdl
    public MonthlyReportResponse saveMonthlyReport(MonthlyReportCreationRequest request)
    {
        Resort resort = resortRepository.findById(request.getIdResort()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        Monthly_Report report = monthlyReportMapper.toMonthlyReport(request);
        report.setIdResort(resort);

        List<Detail_Report> detailReports = new ArrayList<>(); // Danh sách chi tiết thu chi
        BigDecimal totalRevenue = BigDecimal.ZERO; // Biến tổng thu
        BigDecimal totalExpense = BigDecimal.ZERO; // Biến tổng chi

        for (Detail_Report detailReport : request.getDetails())
        {
            Detail_Report detail = new Detail_Report();
            detail.setIdReport(report);
            detail.setType(detailReport.getType());
            detail.setAmount(detailReport.getAmount());
            detail.setCreateDate(LocalDateTime.now());

            if ("Thu".equalsIgnoreCase(detailReport.getType()))
            {
                totalRevenue = totalRevenue.add(detailReport.getAmount());
            }else if ("Chi".equalsIgnoreCase(detailReport.getType()))
            {
                totalExpense = totalExpense.add(detailReport.getAmount());
            }
            detailReports.add(detail); // Lưu đối tượng vào danh sách chi tiết thu chi
        }
        report.setTotalRevenue(totalRevenue);
        report.setTotalExpense(totalExpense);
        report.setNetProfit(totalRevenue.subtract(totalExpense));
        report.setDetails(detailReports);

        var reportReponse = monthlyReportMapper.toMonthlyReportResponse(monthlyReportRepository.save(report));
        reportReponse.setIdReport(report.getIdReport());
        reportReponse.setNameReport(report.getIdResort().getName_rs());
        return reportReponse;
    }

    // Hàm xóa báo cáo
    public void deleteMonthlyReport(String idReport)
    {
        Monthly_Report report = monthlyReportRepository.findById(idReport).orElseThrow(
                () -> new ApiException(ErrorCode.MONTHLYREPORT_NOT_FOUND)
        );
        monthlyReportRepository.delete(report);
    }

    // Hàm sửa báo cáo
    public MonthlyReportResponse updateMonthlyReport(MonthlyReportUpdateRequest request, String idReport)
    {
        Monthly_Report report = monthlyReportRepository.findById(idReport).orElseThrow(
                () -> new ApiException(ErrorCode.MONTHLYREPORT_NOT_FOUND)
        );

        monthlyReportMapper.updateMonthlyReport(report, request);

        // Xóa chi tiết cũ
        report.getDetails().clear();

        List<Detail_Report> newDetails = new ArrayList<>(); // Danh sách chi tiết thu chi
        BigDecimal totalRevenue = BigDecimal.ZERO; // Biến tổng thu
        BigDecimal totalExpense = BigDecimal.ZERO; // Biến tổng chi

        for (Detail_Report detailReport : request.getDetails())
        {
            Detail_Report detail = new Detail_Report();
            detail.setIdReport(report);
            detail.setType(detailReport.getType());
            detail.setAmount(detailReport.getAmount());
            detail.setCreateDate(LocalDateTime.now());

            if ("Thu".equalsIgnoreCase(detailReport.getType()))
            {
                totalRevenue = totalRevenue.add(detailReport.getAmount());
            }else if ("Chi".equalsIgnoreCase(detailReport.getType()))
            {
                totalExpense = totalExpense.add(detailReport.getAmount());
            }
            newDetails.add(detail); // Lưu đối tượng vào danh sách chi tiết thu chi
        }
        report.getDetails().addAll(newDetails);
        report.setTotalRevenue(totalRevenue);
        report.setTotalExpense(totalExpense);
        report.setNetProfit(totalRevenue.subtract(totalExpense));

        var reportReponse = monthlyReportMapper.toMonthlyReportResponse(monthlyReportRepository.save(report));
        reportReponse.setIdReport(report.getIdReport());
        reportReponse.setNameReport(report.getIdResort().getName_rs());
        return reportReponse;
    }
}