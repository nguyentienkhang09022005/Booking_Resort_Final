package com.example.Booking_Resort.service;

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
import com.example.Booking_Resort.repository.BookingRoomRepository;
import com.example.Booking_Resort.repository.BookingServiceRepository;
import com.example.Booking_Resort.repository.ExpenseRepository;
import com.example.Booking_Resort.repository.MonthlyReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MonthlyReportService {
    MonthlyReportRepository monthlyReportRepository;
    ExpenseRepository expenseRepository;
    BookingRoomRepository bookingRoomRepository;

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
                .details(report.getDetails().stream().map(detail -> {
                    String titleOfExpense = null;
                    String titleOfIncome = null;
                    String nameUser = null;

                    if ("Chi".equalsIgnoreCase(detail.getType())) {
                        Expense matchedExpense = expenseRepository.findByIdResort_IdRsAndAmountAndCreateDate(
                                report.getIdResort().getIdRs(),
                                detail.getAmount(),
                                detail.getCreateDate()
                        );
                        if (matchedExpense != null) {
                            titleOfExpense = matchedExpense.getCategory();
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
                        }
                    }

                    return DetailReportResponse.builder()
                            .type(detail.getType())
                            .titleOfExpense(titleOfExpense)
                            .titleOfIncome(titleOfIncome)
                            .amount(detail.getAmount())
                            .createDate(detail.getCreateDate())
                            .build();
                }).collect(Collectors.toList()))

                .build();
    }

    // Hàm lấy dữ liệu của báo cáo theo năm
    public List<MonthlyReportForChartResponse> getMonthlyReportsForYear(MonthlyReportForChartRequest request) {
        // Kiểm tra resort tồn tại
        if (!monthlyReportRepository.existsByIdResort_IdRs(request.getIdResort())) {
            throw new ApiException(ErrorCode.RESORT_NOT_FOUND);
        }

        // Lấy danh sách báo cáo của năm theo resort
        List<Monthly_Report> reports = monthlyReportRepository
                .findByIdResort_IdRsAndReportYear(request.getIdResort(), request.getReportYear());

        // Map báo cáo theo tháng để dễ tra cứu
        Map<Integer, Monthly_Report> reportMap = reports.stream()
                .collect(Collectors.toMap(Monthly_Report::getReportMonth, report -> report));

        List<MonthlyReportForChartResponse> responses = new java.util.ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            Monthly_Report report = reportMap.get(month);

            MonthlyReportForChartResponse response = MonthlyReportForChartResponse.builder()
                    .idReport(report != null ? report.getIdReport() : null)
                    .reportMonth(month)
                    .reportYear(request.getReportYear())
                    .totalRevenue(report != null ? report.getTotalRevenue() : BigDecimal.ZERO)
                    .totalExpense(report != null ? report.getTotalExpense() : BigDecimal.ZERO)
                    .netProfit(report != null ? report.getNetProfit() : BigDecimal.ZERO)
                    .build();

            responses.add(response);
        }
        return responses;
    }
}