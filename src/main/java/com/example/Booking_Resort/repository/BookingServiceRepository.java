package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Booking_Service;
import com.example.Booking_Resort.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingServiceRepository extends JpaRepository<Booking_Service, String> {
    void deleteByIdUser(User user);
}
