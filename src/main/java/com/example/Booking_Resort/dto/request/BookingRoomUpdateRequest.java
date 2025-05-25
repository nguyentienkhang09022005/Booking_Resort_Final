package com.example.Booking_Resort.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRoomUpdateRequest
{
    LocalDateTime checkinday;
    LocalDateTime checkoutday;
    String status;

    List<BookingServiceRequest> services;
}


