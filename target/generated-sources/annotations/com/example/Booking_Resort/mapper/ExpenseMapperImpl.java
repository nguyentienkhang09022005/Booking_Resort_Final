package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.ExpenseCreationRequest;
import com.example.Booking_Resort.dto.request.ExpenseUpdateRequest;
import com.example.Booking_Resort.dto.response.ExpenseResponse;
import com.example.Booking_Resort.models.Expense;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T10:57:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    @Override
    public Expense toExpense(ExpenseCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Expense expense = new Expense();

        expense.setCategory( request.getCategory() );
        expense.setAmount( request.getAmount() );

        return expense;
    }

    @Override
    public ExpenseResponse toExpenseResponse(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseResponse.ExpenseResponseBuilder expenseResponse = ExpenseResponse.builder();

        expenseResponse.category( expense.getCategory() );
        expenseResponse.amount( expense.getAmount() );
        expenseResponse.create_date( expense.getCreate_date() );

        return expenseResponse.build();
    }

    @Override
    public void updateExpense(Expense expense, ExpenseUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        expense.setCategory( request.getCategory() );
        expense.setAmount( request.getAmount() );
    }
}
