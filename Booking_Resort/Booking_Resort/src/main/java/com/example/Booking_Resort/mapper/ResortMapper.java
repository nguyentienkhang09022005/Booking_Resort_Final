package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.models.Resort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ResortMapper {

    @Mapping(target = "idOwner", ignore = true)
    Resort toResort(ResortCreationRequest request);

    ResortResponse toResortRespone(Resort resort);

    void updateResort(@MappingTarget Resort resort, ResortUpdateRequest request);
}
