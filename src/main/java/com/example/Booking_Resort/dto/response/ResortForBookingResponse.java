package com.example.Booking_Resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResortForBookingResponse {
    String name_rs;
    String location_rs;
    String image;
}

