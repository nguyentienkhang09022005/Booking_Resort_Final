package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "idBr", ignore = true)
    Payment toPayment(PaymentCreationRequest request);

    PaymentRespone toPaymentRespone(Payment payment);
}
