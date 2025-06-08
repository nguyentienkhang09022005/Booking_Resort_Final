package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.TypeRoomRequest;
import com.example.Booking_Resort.dto.response.TypeRoomRespone;
import com.example.Booking_Resort.models.Type_Room;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-08T15:01:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class TypeRoomMapperImpl implements TypeRoomMapper {

    @Override
    public Type_Room toTypeRoom(TypeRoomRequest request) {
        if ( request == null ) {
            return null;
        }

        Type_Room type_Room = new Type_Room();

        type_Room.setNameType( request.getNameType() );

        return type_Room;
    }

    @Override
    public TypeRoomRespone toTypeRoomResponse(Type_Room typeRoom) {
        if ( typeRoom == null ) {
            return null;
        }

        TypeRoomRespone.TypeRoomResponeBuilder typeRoomRespone = TypeRoomRespone.builder();

        typeRoomRespone.nameType( typeRoom.getNameType() );

        return typeRoomRespone.build();
    }

    @Override
    public void updateTypeRoom(Type_Room typeRoom, TypeRoomRequest request) {
        if ( request == null ) {
            return;
        }

        typeRoom.setNameType( request.getNameType() );
    }
}
