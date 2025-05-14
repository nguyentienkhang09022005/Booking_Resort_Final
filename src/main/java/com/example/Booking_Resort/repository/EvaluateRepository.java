package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, String> { }
