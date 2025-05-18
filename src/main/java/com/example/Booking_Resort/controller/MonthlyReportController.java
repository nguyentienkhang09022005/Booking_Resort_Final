package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.service.MonthlyReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/report")
public class MonthlyReportController
{
    MonthlyReportService monthlyReportService;

    // Endpoint lấy danh sách báo cáo
    @GetMapping("/list_report")
    public ApiRespone<List<MonthlyReportResponse>> getAllRoom()
    {
        return ApiRespone.<List<MonthlyReportResponse>>builder()
                .data(monthlyReportService.listMonthlyReport())
                .build();
    }
}
