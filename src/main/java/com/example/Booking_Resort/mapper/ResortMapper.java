package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.InfResortResponse;
import com.example.Booking_Resort.dto.response.ResortForBookingResponse;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.models.Resort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ResortMapper {

    @Mapping(target = "idOwner", ignore = true)
    Resort toResort(ResortCreationRequest request);

    @Mapping(target = "image", ignore = true)
    ResortResponse toResortRespone(Resort resort);

    @Mapping(target = "image", ignore = true)
    InfResortResponse toResortInfRespone(Resort resort);

    void updateResort(@MappingTarget Resort resort, ResortUpdateRequest request);

    @Mapping(target = "image", ignore = true)
    ResortForBookingResponse toResortForBookingResponse(Resort resort);

}
