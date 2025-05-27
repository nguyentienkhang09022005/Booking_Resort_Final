package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.ServiceRSCreationRequest;
import com.example.Booking_Resort.dto.request.ServiceRSUpdateRequest;
import com.example.Booking_Resort.dto.response.ServiceRSResponse;
import com.example.Booking_Resort.models.ServiceRS;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-28T01:50:58+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class ServiceRSMapperImpl implements ServiceRSMapper {

    @Override
    public ServiceRS toServiceRS(ServiceRSCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        ServiceRS serviceRS = new ServiceRS();

        serviceRS.setName_sv( request.getName_sv() );
        serviceRS.setPrice( request.getPrice() );
        serviceRS.setDescribe_service( request.getDescribe_service() );

        return serviceRS;
    }

    @Override
    public ServiceRSResponse toServiceRSResponse(ServiceRS serviceRS) {
        if ( serviceRS == null ) {
            return null;
        }

        ServiceRSResponse.ServiceRSResponseBuilder serviceRSResponse = ServiceRSResponse.builder();

        serviceRSResponse.name_sv( serviceRS.getName_sv() );
        serviceRSResponse.price( serviceRS.getPrice() );
        serviceRSResponse.describe_service( serviceRS.getDescribe_service() );

        return serviceRSResponse.build();
    }

    @Override
    public void updateService(ServiceRS serviceRS, ServiceRSUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        serviceRS.setName_sv( request.getName_sv() );
        serviceRS.setPrice( request.getPrice() );
        serviceRS.setDescribe_service( request.getDescribe_service() );
    }
}
