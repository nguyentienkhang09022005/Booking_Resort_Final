package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.FavoriteResortKey;
import com.example.Booking_Resort.models.Favorite_Resort;
import com.example.Booking_Resort.models.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteResortRepository extends JpaRepository<Favorite_Resort, FavoriteResortKey> {

    @Query(value = "SELECT id_rs FROM favorite_resort WHERE id_user = :idUser", nativeQuery = true)
    List<String> findFavoriteResortIdsByUserId(@Param("idUser") String idUser);

    @Query("SELECT fr FROM Favorite_Resort fr JOIN FETCH fr.id_rs WHERE fr.id_user.idUser = :userId")
    List<Favorite_Resort> findFavoriteResortsByUserId(@Param("userId") String userId);
}
