package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.response.FavoriteResortRespone;
import com.example.Booking_Resort.models.Favorite_Resort;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-15T23:03:27+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class FavoriteResortMapperImpl implements FavoriteResortMapper {

    @Override
    public FavoriteResortRespone toFavoriteResortResponse(Favorite_Resort favorite_resort) {
        if ( favorite_resort == null ) {
            return null;
        }

        FavoriteResortRespone.FavoriteResortResponeBuilder favoriteResortRespone = FavoriteResortRespone.builder();

        favoriteResortRespone.created_at( favorite_resort.getCreated_at() );

        return favoriteResortRespone.build();
    }
}
