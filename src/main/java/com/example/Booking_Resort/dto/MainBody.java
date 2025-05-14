package com.example.Booking_Resort.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
public record MainBody(String to, String subject, String text)
{
}
