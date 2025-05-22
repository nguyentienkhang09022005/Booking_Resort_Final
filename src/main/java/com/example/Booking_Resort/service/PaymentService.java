package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.PaymentMapper;
import com.example.Booking_Resort.models.Booking_room;
import com.example.Booking_Resort.models.Payment;
import com.example.Booking_Resort.repository.BookingRoomRepository;
import com.example.Booking_Resort.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)public class PaymentService
{
    PaymentRepository paymentRepository;
    BookingRoomRepository bookingRoomRepository;
    PaymentMapper paymentMapper;

    // Hàm lấy danh sách thanh toán
    public List<PaymentRespone> getAllPayment(String idUser)
    {
        // Lấy danh sách bookingroom của người dùng
        List<Booking_room> bookings = bookingRoomRepository.findByIdUser_IdUser(idUser);
        if (bookings.isEmpty()) {
            throw new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND);
        }

        // Lấy danh sách idBr
        List<String> idBrList = bookings.stream()
                .map(Booking_room::getIdBr)
                .collect(Collectors.toList());

        // Tìm danh sách payment thông qua idBr
        List<Payment> payments = paymentRepository.findByIdBr_IdBrIn(idBrList);
        if (payments.isEmpty()) {
            throw new ApiException(ErrorCode.PAYMENT_NOT_FOUND);
        }

        return payments.stream().map(payment -> PaymentRespone.builder()
                .idPayment(payment.getIdPayment())
                .money(payment.getMoney())
                .create_date(payment.getCreate_date())
                .payment_method(payment.getPayment_method())
                .build()
        ).collect(Collectors.toList());
    }

    // Hàm lấy thông tin chi tiết thanh toán
    public PaymentRespone infPayment(String idPayment)
    {
        Payment payment = paymentRepository.findById(idPayment).orElseThrow(
                () -> new ApiException(ErrorCode.PAYMENT_NOT_FOUND)
        );
        return paymentMapper.toPaymentRespone(payment);
    }

    // Hàm lưu phiếu thanh toán xuống csdl
    public PaymentRespone savePayment(PaymentCreationRequest request)
    {

        Booking_room booking_room = bookingRoomRepository.findById(request.getId_br()).orElseThrow(
                () -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND)
        );

        Payment payment = paymentMapper.toPayment(request);
        payment.setIdBr(booking_room);
        payment.setMoney(booking_room.getTotal_amount());
        paymentRepository.save(payment);

        PaymentRespone paymentRespone = paymentMapper.toPaymentRespone(payment);
        paymentRespone.setPayment_method(payment.getPayment_method());
        paymentRespone.setMoney(payment.getMoney());
        return paymentRespone;
    }

    // Hàm xóa phiếu thanh toán
    public void deletePayment(String idPayment)
    {
        Payment payment = paymentRepository.findById(idPayment).orElseThrow(
                () -> new ApiException(ErrorCode.PAYMENT_NOT_FOUND)
        );

        Booking_room booking_room = bookingRoomRepository.findById(payment.getIdBr().getIdBr()).orElseThrow(
                () -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND)
        );

        // Kiểm tra trạng thái bookingroom
        if ("Đã Xác Nhận".equalsIgnoreCase(booking_room.getStatus())) {
            throw new ApiException(ErrorCode.BOOKING_CONFIRMED_CANNOT_DELETE_PAYMENT);
        }
        paymentRepository.deleteById(idPayment);
    }
}
