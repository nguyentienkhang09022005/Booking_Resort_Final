package com.example.Booking_Resort.dto.response;

import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.Room;
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
    String idBr;
    LocalDateTime checkinday;
    LocalDateTime checkoutday;
    BigDecimal total_amount;
    String status;
    ResortForBookingResponse resortResponse;
    RoomForBookingRespone roomResponse;
    List<BookingServiceResponse> services;
}

