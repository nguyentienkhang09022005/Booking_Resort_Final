package com.example.Booking_Resort.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyReportGetInfomationRequest {
    private String idResort;
    private Integer reportMonth;
    private Integer reportYear;
}
