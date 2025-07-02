package com.example.Booking_Resort.service;

import ch.qos.logback.core.joran.sanity.Pair;
import com.example.Booking_Resort.dto.request.MonthlyReportForChartRequest;
import com.example.Booking_Resort.dto.request.MonthlyReportGetInfomationRequest;
import com.example.Booking_Resort.dto.response.DetailReportResponse;
import com.example.Booking_Resort.dto.response.MonthlyReportForChartResponse;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.models.Booking_room;
import com.example.Booking_Resort.models.Expense;
import com.example.Booking_Resort.models.Monthly_Report;
import com.example.Booking_Resort.models.Room;
import com.example.Booking_Resort.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MonthlyReportService {
    MonthlyReportRepository monthlyReportRepository;
    ExpenseRepository expenseRepository;
    BookingRoomRepository bookingRoomRepository;
    DetailMonthlyReportRepository detailMonthlyReportRepository;
    ResortRepository resortRepository;

    // Hàm chi tiết thông tin báo cáo của tháng/năm
    public MonthlyReportResponse infMonthReport(MonthlyReportGetInfomationRequest request) {
        // Kiểm tra resort tồn tại
        if (!monthlyReportRepository.existsByIdResort_IdRs(request.getIdResort())) {
            throw new ApiException(ErrorCode.RESORT_NOT_FOUND);
        }
        Monthly_Report report = monthlyReportRepository
                .findByIdResort_IdRsAndReportYearAndReportMonth(
                        request.getIdResort(),
                        request.getReportYear(),
                        request.getReportMonth())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorCode.REPORT_NOT_FOUND));

        List<Booking_room> bookingRooms = bookingRoomRepository
                .findByIdRoom_IdRs_IdRs(report.getIdResort().getIdRs());

        return MonthlyReportResponse.builder()
                .idReport(report.getIdReport())
                .nameReport(report.getIdResort().getName_rs())
                .reportMonth(report.getReportMonth())
                .reportYear(report.getReportYear())
                .totalRevenue(report.getTotalRevenue())
                .totalExpense(report.getTotalExpense())
                .netProfit(report.getNetProfit())
                .generatedAt(report.getGeneratedAt())
                .details(report.getDetails().stream()
                        .filter(detail -> detail.getCreateDate().getMonthValue() == request.getReportMonth()
                                && detail.getCreateDate().getYear() == request.getReportYear())
                        .map(detail -> {
                    String titleOfExpense = null;
                    String titleOfIncome = null;
                    String idExpense = null;
                    String idIncome = null;

                    if ("Chi".equalsIgnoreCase(detail.getType())) {
                        Expense matchedExpense = expenseRepository.findByIdResort_IdRsAndAmountAndCreateDate(
                                report.getIdResort().getIdRs(),
                                detail.getAmount(),
                                detail.getCreateDate()
                        );
                        if (matchedExpense != null) {
                            titleOfExpense = matchedExpense.getCategory();
                            idExpense = matchedExpense.getIdExpense();
                        }
                    }

                    if ("Thu".equalsIgnoreCase(detail.getType())) {
                        Optional<Booking_room> matchedBooking = bookingRooms.stream()
                                .filter(br -> br.getTotal_amount().compareTo(detail.getAmount()) == 0
                                        && br.getCreate_date().toLocalDate().equals(detail.getCreateDate().toLocalDate()))
                                .findFirst();

                        if (matchedBooking.isPresent()) {
                            Room room = matchedBooking.get().getIdRoom();
                            titleOfIncome = room != null ? room.getName_room() : null;
                            idIncome = matchedBooking.get().getIdBr();
                        }
                    }

                    return DetailReportResponse.builder()
                            .type(detail.getType())
                            .titleOfExpense(titleOfExpense)
                            .titleOfIncome(titleOfIncome)
                            .idExpense(idExpense)
                            .idIncome(idIncome)
                            .amount(detail.getAmount())
                            .createDate(detail.getCreateDate())
                            .build();
                }).collect(Collectors.toList()))

                .build();
    }

    // Hàm lấy dữ liệu của báo cáo theo năm
    public List<MonthlyReportForChartResponse> getMonthlyReportsForYear(MonthlyReportForChartRequest request) {
        if (!resortRepository.existsById(request.getIdResort())) {
            throw new ApiException(ErrorCode.RESORT_NOT_FOUND);
        }

        List<Object[]> rawData = detailMonthlyReportRepository.getMonthlyRevenueExpense(
                request.getIdResort(), request.getReportYear());

        // Map kết quả từ query thành Map<month, response>
        Map<Integer, MonthlyReportForChartResponse> reportMap = new HashMap<>();
        for (Object[] row : rawData) {
            int month = ((Number) row[0]).intValue();
            BigDecimal revenue = (BigDecimal) row[1];
            BigDecimal expense = (BigDecimal) row[2];

            reportMap.put(month, MonthlyReportForChartResponse.builder()
                    .idReport(null)
                    .reportMonth(month)
                    .reportYear(request.getReportYear())
                    .totalRevenue(revenue)
                    .totalExpense(expense)
                    .netProfit(revenue.subtract(expense))
                    .build());
        }

        // Đảm bảo trả đủ 12 tháng
        List<MonthlyReportForChartResponse> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            MonthlyReportForChartResponse report = reportMap.getOrDefault(month,
                    MonthlyReportForChartResponse.builder()
                            .idReport(null)
                            .reportMonth(month)
                            .reportYear(request.getReportYear())
                            .totalRevenue(BigDecimal.ZERO)
                            .totalExpense(BigDecimal.ZERO)
                            .netProfit(BigDecimal.ZERO)
                            .build());
            result.add(report);
        }

        return result;
    }
}