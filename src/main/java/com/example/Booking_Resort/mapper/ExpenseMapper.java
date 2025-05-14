package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.ExpenseCreationRequest;
import com.example.Booking_Resort.dto.request.ExpenseUpdateRequest;
import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.ExpenseResponse;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.models.Expense;
import com.example.Booking_Resort.models.Resort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "idResort", ignore = true)
    Expense toExpense(ExpenseCreationRequest request);

    ExpenseResponse toExpenseResponse(Expense expense);

    void updateExpense(@MappingTarget Expense expense, ExpenseUpdateRequest request);
}
