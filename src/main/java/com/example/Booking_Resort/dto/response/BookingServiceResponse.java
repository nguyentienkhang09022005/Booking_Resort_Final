package com.example.Booking_Resort.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingServiceResponse {
    String nameService;
    Integer quantity;
    BigDecimal total_amount;
}