package com.example.Booking_Resort.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRSResponse
{
    String idService;
    String name_sv;
    BigDecimal price;
    String describe_service;
}
