package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Booking_Service;
import com.example.Booking_Resort.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingServiceRepository extends JpaRepository<Booking_Service, String> {
    void deleteByIdUser(User user);

    List<Booking_Service> findByIdBr_IdBr(String idBr);

    // BookingServiceRepository
    List<Booking_Service> findByIdBr_IdBrIn(List<String> idBrs);
}
