package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.MonthlyReportCreationRequest;
import com.example.Booking_Resort.dto.request.MonthlyReportUpdateRequest;
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

    // Endpoint tạo báo cáo tháng
    @PostMapping("/create_report")
    public ApiRespone<MonthlyReportResponse> createMonthlyReport(@RequestBody MonthlyReportCreationRequest request)
    {
        return ApiRespone.<MonthlyReportResponse>builder()
                .message("successful creation")
                .data(monthlyReportService.saveMonthlyReport(request))
                .build();
    }

    // Endpoint xóa báo cáo
    @DeleteMapping("/delete_report/{idReport}")
    public ApiRespone<String> deleteMonthlyReport(@PathVariable String idReport)
    {
        monthlyReportService.deleteMonthlyReport(idReport);
        return ApiRespone.<String>builder()
                .message("successful deletion")
                .build();
    }

    // Endpoint sửa thông tin báo cáo
    @PutMapping("/update_report/{idReport}")
    public ApiRespone<MonthlyReportResponse> updateMonthlyReport(@RequestBody MonthlyReportUpdateRequest request,
                                                       @PathVariable String idReport)
    {
        return ApiRespone.<MonthlyReportResponse>builder()
                .message("successful updating")
                .data(monthlyReportService.updateMonthlyReport(request, idReport))
                .build();
    }
}
