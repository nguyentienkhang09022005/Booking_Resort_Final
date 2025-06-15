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

    @Query("""
    SELECT COUNT(e) > 0 FROM Evaluate e 
    WHERE e.idUser.idUser = :userId AND e.idRs.idRs = :resortId
""")
    boolean existsEvaluation(@Param("userId") String userId, @Param("resortId") String resortId);

    // Lấy tất cả đánh giá theo danh sách idRs
    List<Evaluate> findByIdRs_IdRsIn(List<String> idRs);

    // Lấy đánh giá trung bình cho từng resort
    @Query("SELECT e.idRs.idRs, AVG(e.star_rating) FROM Evaluate e WHERE e.idRs.idRs IN :resortIds GROUP BY e.idRs.idRs")
    List<Object[]> getAverageRatingsByResortIds(@Param("resortIds") List<String> resortIds);
}
