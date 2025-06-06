package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.RoomCreationRequest;
import com.example.Booking_Resort.dto.request.RoomUpdateRequest;
import com.example.Booking_Resort.dto.response.RoomForBookingRespone;
import com.example.Booking_Resort.dto.response.RoomRespone;
import com.example.Booking_Resort.models.Room;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-07T02:42:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room toRoom(RoomCreationRequest roomCreationRequest) {
        if ( roomCreationRequest == null ) {
            return null;
        }

        Room room = new Room();

        room.setName_room( roomCreationRequest.getName_room() );
        room.setPrice( roomCreationRequest.getPrice() );
        room.setDescribe_room( roomCreationRequest.getDescribe_room() );

        return room;
    }

    @Override
    public RoomRespone toRoomRespone(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomRespone.RoomResponeBuilder roomRespone = RoomRespone.builder();

        roomRespone.idRoom( room.getIdRoom() );
        roomRespone.name_room( room.getName_room() );
        roomRespone.price( room.getPrice() );
        roomRespone.status( room.getStatus() );
        roomRespone.describe_room( room.getDescribe_room() );

        return roomRespone.build();
    }

    @Override
    public void updateRoom(Room room, RoomUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        room.setName_room( request.getName_room() );
        room.setPrice( request.getPrice() );
        room.setStatus( request.getStatus() );
        room.setDescribe_room( request.getDescribe_room() );
    }

    @Override
    public RoomForBookingRespone toRoomForBookingResponse(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomForBookingRespone.RoomForBookingResponeBuilder roomForBookingRespone = RoomForBookingRespone.builder();

        roomForBookingRespone.name_room( room.getName_room() );
        roomForBookingRespone.price( room.getPrice() );

        return roomForBookingRespone.build();
    }
}
