package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.MonthlyReportGetInfomationRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.service.MonthlyReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/report")
public class MonthlyReportController
{
    MonthlyReportService monthlyReportService;

    // Endpoint lấy danh sách báo cáo
    @PostMapping("/list_report")
    public ApiRespone<MonthlyReportResponse> getinfReport(@RequestBody MonthlyReportGetInfomationRequest request)
    {
        System.out.println("Year: " + request.getReportYear());
        System.out.println("Month: " + request.getReportMonth());
        return ApiRespone.<MonthlyReportResponse>builder()
                .data(monthlyReportService.infMonthReport(request))
                .build();
    }
}
