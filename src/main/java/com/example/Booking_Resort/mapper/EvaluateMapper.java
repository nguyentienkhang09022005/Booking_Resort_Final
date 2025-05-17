package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.EvaluateCreationRequest;
import com.example.Booking_Resort.dto.request.EvaluateUpdateRequest;
import com.example.Booking_Resort.dto.response.EvaluateRespone;
import com.example.Booking_Resort.models.Evaluate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EvaluateMapper
{
    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "idRs", ignore = true)
    Evaluate toEvaluate(EvaluateCreationRequest request);

    EvaluateRespone toEvaluateRespone(Evaluate respone);

    void updateEvaluate(@MappingTarget Evaluate evaluate, EvaluateUpdateRequest request);
}
