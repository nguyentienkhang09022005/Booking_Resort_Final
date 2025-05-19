package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Booking_room;
import com.example.Booking_Resort.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRoomRepository extends JpaRepository<Booking_room, String>
{
    List<Booking_room> findByIdUser_IdUser(String idUser);

}
