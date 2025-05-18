package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.BookingRoomRequest;
import com.example.Booking_Resort.dto.request.BookingRoomUpdateRequest;
import com.example.Booking_Resort.models.Booking_room;
import com.example.Booking_Resort.dto.response.BookingRoomRespone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id_user", ignore = true)
    @Mapping(target = "id_room", ignore = true)
    @Mapping(target = "create_date", ignore = true)
    Booking_room toBookingRoom(BookingRoomRequest request);

    BookingRoomRespone toBookingRespone(Booking_room bookingRoom);

    void updateBookingRoom(@MappingTarget Booking_room bookingRoom, BookingRoomUpdateRequest request);

}
