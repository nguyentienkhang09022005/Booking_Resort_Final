package com.example.Booking_Resort.dto.request;

import com.example.Booking_Resort.models.Detail_Report;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyReportUpdateRequest {
    private Integer reportMonth;
    private Integer reportYear;
    private List<Detail_Report> details;
}
