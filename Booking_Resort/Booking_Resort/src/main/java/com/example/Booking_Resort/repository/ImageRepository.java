package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Image;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.Room;
import com.example.Booking_Resort.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    void deleteByIdUser(User user);
    void deleteByIdRoom(Room room);
    void deleteByIdRs(Resort resort);
}
