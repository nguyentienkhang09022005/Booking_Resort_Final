package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.request.PaymentUpdateRequest;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.models.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T10:57:16+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toPayment(PaymentCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setMoney( request.getMoney() );
        payment.setPayment_method( request.getPayment_method() );

        return payment;
    }

    @Override
    public PaymentRespone toPaymentRespone(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentRespone.PaymentResponeBuilder paymentRespone = PaymentRespone.builder();

        paymentRespone.money( payment.getMoney() );
        paymentRespone.create_date( payment.getCreate_date() );
        paymentRespone.status( payment.getStatus() );
        paymentRespone.payment_method( payment.getPayment_method() );

        return paymentRespone.build();
    }

    @Override
    public void updatePayment(Payment payment, PaymentUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        payment.setMoney( request.getMoney() );
        payment.setStatus( request.getStatus() );
        payment.setPayment_method( request.getPayment_method() );
    }
}
