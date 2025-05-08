package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.MonthlyReportCreationRequest;
import com.example.Booking_Resort.dto.request.MonthlyReportUpdateRequest;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.models.Monthly_Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MonthlyReportMapper {

    @Mapping(target = "idResort", ignore = true)
    Monthly_Report toMonthlyReport(MonthlyReportCreationRequest request);

    MonthlyReportResponse toMonthlyReportResponse(Monthly_Report report);

    void updateMonthlyReport(@MappingTarget Monthly_Report report, MonthlyReportUpdateRequest request);
}
