package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.ServiceRSCreationRequest;
import com.example.Booking_Resort.dto.request.ServiceRSUpdateRequest;
import com.example.Booking_Resort.dto.response.ServiceRSResponse;
import com.example.Booking_Resort.models.ServiceRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceRSMapper {

    @Mapping(target = "idRs", ignore = true)
    ServiceRS toServiceRS(ServiceRSCreationRequest request);

    ServiceRSResponse toServiceRSResponse(ServiceRS serviceRS);

    void updateService(@MappingTarget ServiceRS serviceRS, ServiceRSUpdateRequest request);
}
