package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, String> {
    @Query("SELECT AVG(e.start_rating) FROM Evaluate e WHERE e.id_rs.idRs = :idRs")
    Double getAverageStartRatingByResort(@Param("idRs") String idRs);
}
