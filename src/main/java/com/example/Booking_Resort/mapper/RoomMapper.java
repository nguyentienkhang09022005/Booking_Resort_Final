package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.RoomCreationRequest;
import com.example.Booking_Resort.dto.request.RoomUpdateRequest;
import com.example.Booking_Resort.dto.response.RoomRespone;
import com.example.Booking_Resort.models.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper
{
    @Mapping(target = "id_rs", ignore = true)
    @Mapping(target = "id_type", ignore = true)
    Room toRoom(RoomCreationRequest roomCreationRequest);

    @Mapping(target = "image", ignore = true)
    RoomRespone toRoomRespone(Room room);

    @Mapping(target = "id_type", ignore = true)
    void updateRoom(@MappingTarget Room room, RoomUpdateRequest request);
}
