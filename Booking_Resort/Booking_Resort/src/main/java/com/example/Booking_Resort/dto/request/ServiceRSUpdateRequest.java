package com.example.Booking_Resort.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRSUpdateRequest
{
    private String name_sv;
    private BigDecimal price;
    private String describe_service;
}
