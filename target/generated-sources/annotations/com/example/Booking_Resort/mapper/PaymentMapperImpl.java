package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.models.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-15T22:58:46+0700",
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

        payment.setPayment_method( request.getPayment_method() );

        return payment;
    }

    @Override
    public PaymentRespone toPaymentRespone(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentRespone.PaymentResponeBuilder paymentRespone = PaymentRespone.builder();

        paymentRespone.idPayment( payment.getIdPayment() );
        paymentRespone.money( payment.getMoney() );
        paymentRespone.create_date( payment.getCreate_date() );
        paymentRespone.payment_method( payment.getPayment_method() );

        return paymentRespone.build();
    }
}
