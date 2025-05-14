package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.request.PaymentUpdateRequest;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.BookingMapper;
import com.example.Booking_Resort.mapper.PaymentMapper;
import com.example.Booking_Resort.models.Booking_room;
import com.example.Booking_Resort.models.Payment;
import com.example.Booking_Resort.repository.BookingRoomRepository;
import com.example.Booking_Resort.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)public class PaymentService
{
    PaymentRepository paymentRepository;
    BookingRoomRepository bookingRoomRepository;
    PaymentMapper paymentMapper;

    // Hàm lấy danh sách thanh toán
    public List<Payment> getAllPayment()
    {
        return this.paymentRepository.findAll();
    }

    // Hàm lưu phiếu thanh toán xuống csdl
    public PaymentRespone savePayment(PaymentCreationRequest request)
    {
        Booking_room booking_room = bookingRoomRepository.findById(request.getId_br()).orElseThrow(
                () -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND)
        );

        Payment payment = paymentMapper.toPayment(request);
        payment.setId_br(booking_room);
        paymentRepository.save(payment);
        return paymentMapper.toPaymentRespone(payment);
    }

    // Hàm sửa phiếu thanh toán
    public PaymentRespone changePayment(PaymentUpdateRequest request, String idPayment)
    {
        Payment payment = paymentRepository.findById(idPayment).orElseThrow(
                () -> new ApiException(ErrorCode.PAYMENT_NOT_FOUND)
        );

        paymentMapper.updatePayment(payment, request);
        return paymentMapper.toPaymentRespone(paymentRepository.save(payment));
    }

    // Hàm xóa phiếu thanh toán
    public void deletePayment(String idPayment)
    {
        Payment payment = paymentRepository.findById(idPayment).orElseThrow(
                () -> new ApiException(ErrorCode.PAYMENT_NOT_FOUND)
        );
        paymentRepository.deleteById(idPayment);
    }
}
