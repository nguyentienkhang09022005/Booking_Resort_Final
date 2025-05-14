package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.ExpenseCreationRequest;
import com.example.Booking_Resort.dto.request.ExpenseUpdateRequest;
import com.example.Booking_Resort.dto.response.ExpenseResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.ExpenseMapper;
import com.example.Booking_Resort.models.Expense;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.repository.ExpenseRepository;
import com.example.Booking_Resort.repository.ResortRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseService {
    ExpenseRepository expenseRepository;
    ExpenseMapper expenseMapper;
    ResortRepository resortRepository;

    // Hàm lưu chi tiêu xuống csdl
    public ExpenseResponse saveExpense(ExpenseCreationRequest request)
    {
        Resort resort = resortRepository.findById(request.getIdResort()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        Expense expense = expenseMapper.toExpense(request);
        expense.setIdResort(resort);
        expenseRepository.save(expense);
        return expenseMapper.toExpenseResponse(expense);
    }

    // Hàm xóa chi tiêu
    public void deleteExpense(String idExpense)
    {
        Expense expense = expenseRepository.findById(idExpense).orElseThrow(
                () -> new ApiException(ErrorCode.EXPENSE_NOT_FOUND)
        );
        expenseRepository.delete(expense);
    }

    // Hàm thay đổi nội dung chi tiêu
    public ExpenseResponse updateExpense(ExpenseUpdateRequest request, String idExpense)
    {
        Expense expense = expenseRepository.findById(idExpense).orElseThrow(
                () -> new ApiException(ErrorCode.EXPENSE_NOT_FOUND)
        );
        expenseMapper.updateExpense(expense, request);
        return expenseMapper.toExpenseResponse(expenseRepository.save(expense));
    }
}