package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.BookingRoomRequest;
import com.example.Booking_Resort.dto.request.BookingRoomUpdateRequest;
import com.example.Booking_Resort.dto.response.BookingRoomRespone;
import com.example.Booking_Resort.models.Booking_room;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T15:20:58+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public Booking_room toBookingRoom(BookingRoomRequest request) {
        if ( request == null ) {
            return null;
        }

        Booking_room booking_room = new Booking_room();

        booking_room.setCheckinday( request.getCheckinday() );
        booking_room.setCheckoutday( request.getCheckoutday() );

        return booking_room;
    }

    @Override
    public BookingRoomRespone toBookingRespone(Booking_room bookingRoom) {
        if ( bookingRoom == null ) {
            return null;
        }

        BookingRoomRespone.BookingRoomResponeBuilder bookingRoomRespone = BookingRoomRespone.builder();

        bookingRoomRespone.checkinday( bookingRoom.getCheckinday() );
        bookingRoomRespone.checkoutday( bookingRoom.getCheckoutday() );
        bookingRoomRespone.total_amount( bookingRoom.getTotal_amount() );
        bookingRoomRespone.status( bookingRoom.getStatus() );

        return bookingRoomRespone.build();
    }

    @Override
    public void updateBookingRoom(Booking_room bookingRoom, BookingRoomUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        bookingRoom.setCheckinday( request.getCheckinday() );
        bookingRoom.setCheckoutday( request.getCheckoutday() );
    }
}
