package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.request.PaymentUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.models.Payment;
import com.example.Booking_Resort.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)@RequestMapping("/api/payment")
public class PaymentController
{
    PaymentService paymentService;

    // Endpoint lấy danh sách thanh toán
    @GetMapping
    public ApiRespone<List<Payment>> getAllPayment()
    {
        return ApiRespone.<List<Payment>>builder()
                .data(paymentService.getAllPayment())
                .build();
    }

    // Endpoint tạo phiếu thanh toán
    @PostMapping("/create_payment")
    public ApiRespone<PaymentRespone> createPayment(@RequestBody PaymentCreationRequest request)
    {
        return ApiRespone.<PaymentRespone>builder()
                .message("successful creation")
                .data(paymentService.savePayment(request))
                .build();
    }

    // Endpoint thay đổi thông tin phiếu thanh toán
    @PutMapping("/change_payment/{idPayment}")
    public ApiRespone<PaymentRespone> changePayment(@RequestBody PaymentUpdateRequest request,
                                                    @PathVariable String idPayment)
    {
        return ApiRespone.<PaymentRespone>builder()
                .message("successful update payment")
                .data(paymentService.changePayment(request,idPayment))
                .build();
    }

    // Endpoint xóa phiếu thanh toán
    @DeleteMapping("/delete_payment/{idPayment}")
    public ApiRespone<String> deletePayment(@PathVariable String idPayment)
    {
        paymentService.deletePayment(idPayment);
        return ApiRespone.<String>builder()
                .message("successful delete payment")
                .build();
    }
}
