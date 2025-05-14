package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.EvaluateCreationRequest;
import com.example.Booking_Resort.dto.request.EvaluateUpdateRequest;
import com.example.Booking_Resort.dto.response.EvaluateRespone;
import com.example.Booking_Resort.models.Evaluate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-14T17:39:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class EvaluateMapperImpl implements EvaluateMapper {

    @Override
    public Evaluate toEvaluate(EvaluateCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Evaluate evaluate = new Evaluate();

        evaluate.setUser_comment( request.getUser_comment() );
        evaluate.setStart_rating( request.getStart_rating() );

        return evaluate;
    }

    @Override
    public EvaluateRespone toEvaluateRespone(Evaluate respone) {
        if ( respone == null ) {
            return null;
        }

        EvaluateRespone.EvaluateResponeBuilder evaluateRespone = EvaluateRespone.builder();

        evaluateRespone.user_comment( respone.getUser_comment() );
        evaluateRespone.start_rating( respone.getStart_rating() );
        evaluateRespone.create_date( respone.getCreate_date() );

        return evaluateRespone.build();
    }

    @Override
    public void updateEvaluate(Evaluate evaluate, EvaluateUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        evaluate.setUser_comment( request.getUser_comment() );
        evaluate.setStart_rating( request.getStart_rating() );
    }
}
