package com.example.Booking_Resort.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRoomRespone {
    LocalDateTime checkinday;
    LocalDateTime checkoutday;
    BigDecimal total_amount;
    String status;
    List<BookingServiceResponse> services;
}

