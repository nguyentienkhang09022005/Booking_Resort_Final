package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Type_Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRoomRepository extends JpaRepository<Type_Room, String> {
}
