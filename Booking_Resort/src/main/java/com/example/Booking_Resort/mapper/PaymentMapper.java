package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.request.PaymentUpdateRequest;
import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.models.Payment;
import com.example.Booking_Resort.models.Resort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id_br", ignore = true)
    Payment toPayment(PaymentCreationRequest request);

    PaymentRespone toPaymentRespone(Payment payment);

    void updatePayment(@MappingTarget Payment payment, PaymentUpdateRequest request);
}
