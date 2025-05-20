package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.ExpenseCreationRequest;
import com.example.Booking_Resort.dto.request.ExpenseUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.ExpenseResponse;
import com.example.Booking_Resort.service.ExpenseService;
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
@RequestMapping("/api/expense")
public class ExpenseController
{
    ExpenseService expenseService;

    // Endpoint lấy danh sách chi tiêu
    @GetMapping("/list_expense/{idResort}")
    public ApiRespone<List<ExpenseResponse>> getListExpense(@PathVariable String idResort)
    {
        return ApiRespone.<List<ExpenseResponse>>builder()
                .data(expenseService.listExpenses(idResort))
                .build();
    }

    // Endpoint lấy thông tin chi tiêu
    @GetMapping("/inf_Expense/{idExpense}")
    public ApiRespone<ExpenseResponse> getInfExpense(@PathVariable String idExpense)
    {
        return ApiRespone.<ExpenseResponse>builder()
                .data(expenseService.infExpense(idExpense))
                .build();
    }

    // Endpoint tạo chi tiêu
    @PostMapping("/create_expense")
    public ApiRespone<ExpenseResponse> createExpense(@RequestBody ExpenseCreationRequest request)
    {
        return ApiRespone.<ExpenseResponse>builder()
                .message("successful creation")
                .data(expenseService.saveExpense(request))
                .build();
    }

    // Endpoint xóa chi tiêu
    @DeleteMapping("/delete_expense/{idExpense}")
    public ApiRespone<String> deleteService(@PathVariable String idExpense)
    {
        expenseService.deleteExpense(idExpense);
        return ApiRespone.<String>builder()
                .message("successful deletion")
                .build();
    }

    // Endpoint sửa thông tin chi tiêu
    @PutMapping("/update_expense/{idExpense}")
    public ApiRespone<ExpenseResponse> updateExpense(@RequestBody ExpenseUpdateRequest request,
                                                       @PathVariable String idExpense)
    {
        return ApiRespone.<ExpenseResponse>builder()
                .message("successful updating")
                .data(expenseService.updateExpense(request, idExpense))
                .build();
    }
}
