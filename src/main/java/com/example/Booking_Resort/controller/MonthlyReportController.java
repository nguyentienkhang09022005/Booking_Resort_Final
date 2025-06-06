package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.MonthlyReportForChartRequest;
import com.example.Booking_Resort.dto.request.MonthlyReportGetInfomationRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.MonthlyReportForChartResponse;
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

    // Endpoint lấy chi tiết báo cáo theo tháng của năm
    @PostMapping("/inf_report")
    public ApiRespone<MonthlyReportResponse> getinfReport(@RequestBody MonthlyReportGetInfomationRequest request)
    {
        return ApiRespone.<MonthlyReportResponse>builder()
                .data(monthlyReportService.infMonthReport(request))
                .build();
    }

    // Endpoint lấy danh sách báo cáo theo các tháng của năm
    @PostMapping("/list_report")
    public ApiRespone<List<MonthlyReportForChartResponse>> getListReport(@RequestBody MonthlyReportForChartRequest request)
    {
        return ApiRespone.<List<MonthlyReportForChartResponse>>builder()
                .data(monthlyReportService.getMonthlyReportsForYear(request))
                .build();
    }
}
