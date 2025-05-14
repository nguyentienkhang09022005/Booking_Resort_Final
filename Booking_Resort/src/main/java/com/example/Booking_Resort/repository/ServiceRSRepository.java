package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.ServiceRS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRSRepository extends JpaRepository<ServiceRS, String> {
}
