package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResortRepository extends JpaRepository<Resort, String> {
    List<Resort> findByIdOwner_IdUser(String idUser);
}