package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.TypeRoomRequest;
import com.example.Booking_Resort.dto.response.TypeRoomRespone;
import com.example.Booking_Resort.models.Type_Room;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TypeRoomMapper
{
    Type_Room toTypeRoom(TypeRoomRequest request);

    TypeRoomRespone toTypeRoomResponse(Type_Room typeRoom);

    void updateTypeRoom(@MappingTarget Type_Room typeRoom, TypeRoomRequest request);
}
