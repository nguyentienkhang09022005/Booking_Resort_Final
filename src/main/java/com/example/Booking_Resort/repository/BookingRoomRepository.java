package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Booking_room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRoomRepository extends JpaRepository<Booking_room, String>
{
}
