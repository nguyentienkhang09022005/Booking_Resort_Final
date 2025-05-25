package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.ResortForBookingResponse;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.models.Resort;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T00:35:44+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class ResortMapperImpl implements ResortMapper {

    @Override
    public Resort toResort(ResortCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Resort resort = new Resort();

        resort.setName_rs( request.getName_rs() );
        resort.setLocation_rs( request.getLocation_rs() );
        resort.setDescribe_rs( request.getDescribe_rs() );

        return resort;
    }

    @Override
    public ResortResponse toResortRespone(Resort resort) {
        if ( resort == null ) {
            return null;
        }

        ResortResponse.ResortResponseBuilder resortResponse = ResortResponse.builder();

        resortResponse.idRs( resort.getIdRs() );
        resortResponse.name_rs( resort.getName_rs() );
        resortResponse.location_rs( resort.getLocation_rs() );
        resortResponse.describe_rs( resort.getDescribe_rs() );

        return resortResponse.build();
    }

    @Override
    public void updateResort(Resort resort, ResortUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        resort.setName_rs( request.getName_rs() );
        resort.setLocation_rs( request.getLocation_rs() );
        resort.setDescribe_rs( request.getDescribe_rs() );
    }

    @Override
    public ResortForBookingResponse toResortForBookingResponse(Resort resort) {
        if ( resort == null ) {
            return null;
        }

        ResortForBookingResponse.ResortForBookingResponseBuilder resortForBookingResponse = ResortForBookingResponse.builder();

        resortForBookingResponse.name_rs( resort.getName_rs() );
        resortForBookingResponse.location_rs( resort.getLocation_rs() );

        return resortForBookingResponse.build();
    }
}
