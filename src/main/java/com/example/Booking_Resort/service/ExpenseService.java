package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.ExpenseCreationRequest;
import com.example.Booking_Resort.dto.request.ExpenseUpdateRequest;
import com.example.Booking_Resort.dto.response.ExpenseResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.ExpenseMapper;
import com.example.Booking_Resort.models.Detail_Report;
import com.example.Booking_Resort.models.Expense;
import com.example.Booking_Resort.models.Monthly_Report;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.repository.ExpenseRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseService {
    ExpenseRepository expenseRepository;
    ExpenseMapper expenseMapper;
    ResortRepository resortRepository;
    MonthlyReportRepository monthlyReportRepository;

    // Hàm lưu chi tiêu xuống csdl
    public ExpenseResponse saveExpense(ExpenseCreationRequest request)
    {
        Resort resort = resortRepository.findById(request.getIdResort()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        // Gán 1 lần giá trị cho expense và detail_report
        LocalDateTime now = LocalDateTime.now().withNano(0);

        Expense expense = expenseMapper.toExpense(request);
        expense.setCreate_date(now);
        expense.setIdResort(resort);
        expenseRepository.save(expense);

        // Lấy tháng năm hiện tại
        int month = now.getMonthValue();
        int year = now.getYear();

        // Tạo báo cáo nếu chưa có
        Monthly_Report report = monthlyReportRepository
                .findByReportMonthAndReportYearAndIdResort(month, year, resort)
                .orElseGet(() -> {
                    Monthly_Report newReport = new Monthly_Report();
                    newReport.setReportMonth(month);
                    newReport.setReportYear(year);
                    newReport.setIdResort(resort);
                    newReport.setTotalRevenue(BigDecimal.ZERO);
                    newReport.setTotalExpense(BigDecimal.ZERO);
                    newReport.setNetProfit(BigDecimal.ZERO);
                    newReport.setDetails(new ArrayList<>());
                    return monthlyReportRepository.save(newReport);
                });

        // Tạo chi tiết báo cáo
        Detail_Report detailReport = new Detail_Report();
        detailReport.setIdReport(report);
        detailReport.setType("Chi");
        detailReport.setAmount(expense.getAmount());
        detailReport.setCreateDate(now);

        report.getDetails().add(detailReport);
        report.setTotalExpense(report.getTotalExpense().add(detailReport.getAmount()));
        report.setNetProfit(report.getTotalRevenue().subtract(report.getTotalExpense()));
        monthlyReportRepository.save(report);

        return expenseMapper.toExpenseResponse(expense);
    }

    // Hàm xóa chi tiêu
    public void deleteExpense(String idExpense)
    {
        Expense expense = expenseRepository.findById(idExpense)
                .orElseThrow(() -> new ApiException(ErrorCode.EXPENSE_NOT_FOUND));

        Resort resort = expense.getIdResort();
        LocalDateTime expenseDate = expense.getCreate_date();
        int month = expenseDate.getMonthValue();
        int year = expenseDate.getYear();

        Monthly_Report report = monthlyReportRepository
                .findByReportMonthAndReportYearAndIdResort(month, year, resort)
                .orElse(null);

        if (report != null) {
            // Tìm và xóa chi tiết báo cáo tương ứng
            report.getDetails().removeIf(detail ->
                    detail.getType().equals("Chi") &&
                            detail.getAmount().compareTo(expense.getAmount()) == 0 &&
                            detail.getCreateDate().equals(expenseDate)
            );

            // Cập nhật lại tổng chi và lợi nhuận
            report.setTotalExpense(report.getTotalExpense().subtract(expense.getAmount()));
            report.setNetProfit(report.getTotalRevenue().subtract(report.getTotalExpense()));
            monthlyReportRepository.save(report);
        }
        expenseRepository.delete(expense);
    }

    // Hàm thay đổi nội dung chi tiêu
    public ExpenseResponse updateExpense(ExpenseUpdateRequest request, String idExpense)
    {
        Expense expense = expenseRepository.findById(idExpense)
                .orElseThrow(() -> new ApiException(ErrorCode.EXPENSE_NOT_FOUND));

        Resort resort = expense.getIdResort();
        LocalDateTime expenseDate = expense.getCreate_date();
        int month = expenseDate.getMonthValue();
        int year = expenseDate.getYear();

        BigDecimal oldAmount = expense.getAmount();

        expenseMapper.updateExpense(expense, request);
        Expense updatedExpense = expenseRepository.save(expense);

        BigDecimal newAmount = updatedExpense.getAmount();

        // Tìm report tương ứng
        Monthly_Report report = monthlyReportRepository
                .findByReportMonthAndReportYearAndIdResort(month, year, resort)
                .orElseThrow(() -> new ApiException(ErrorCode.REPORT_NOT_FOUND));

        // Tìm detail_report tương ứng
        Detail_Report detailReport = report.getDetails().stream()
                .filter(detail -> detail.getType().equals("Chi")
                        && detail.getAmount().compareTo(oldAmount) == 0
                        && detail.getCreateDate().withNano(0).equals(expenseDate))
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorCode.DETAIL_REPORT_NOT_FOUND));

        // Cập nhật chi tiết báo cáo
        detailReport.setAmount(newAmount);

        // Cập nhật lại totalExpense và netProfit
        BigDecimal updatedExpenseTotal = report.getTotalExpense().subtract(oldAmount).add(newAmount);
        report.setTotalExpense(updatedExpenseTotal);
        report.setNetProfit(report.getTotalRevenue().subtract(updatedExpenseTotal));

        // Lưu cả detailReport và report
        monthlyReportRepository.save(report);
        return expenseMapper.toExpenseResponse(updatedExpense);
    }
}