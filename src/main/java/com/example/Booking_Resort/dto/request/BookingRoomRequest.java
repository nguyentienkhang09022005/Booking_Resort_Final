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
public class BookingRoomRequest
{
    String id_user;
    String id_room;
    LocalDateTime checkinday;
    LocalDateTime checkoutday;

    List<BookingServiceRequest> services;
}


