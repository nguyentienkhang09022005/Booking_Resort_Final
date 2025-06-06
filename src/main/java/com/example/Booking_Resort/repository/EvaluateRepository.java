package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, String> {
    @Query("SELECT AVG(e.star_rating) FROM Evaluate e WHERE e.idRs.idRs = :idRs")
    Double getAverageStartRatingByResort(@Param("idRs") String idRs);

    List<Evaluate> findByIdRs_IdRs(String idRs);

    boolean existsByIdUser_IdUserAndIdRs_IdRs(String idUser, String idRs);
}
