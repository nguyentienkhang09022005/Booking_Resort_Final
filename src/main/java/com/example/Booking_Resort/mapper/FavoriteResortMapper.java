package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.response.FavoriteResortRespone;
import com.example.Booking_Resort.models.Favorite_Resort;
import com.example.Booking_Resort.models.Resort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteResortMapper {
    @Mapping(source = "created_at", target = "created_at")
    FavoriteResortRespone toFavoriteResortResponse(Favorite_Resort favorite_resort);

    FavoriteResortRespone ResortToFavoriteResortResponse(Resort resort);
}
