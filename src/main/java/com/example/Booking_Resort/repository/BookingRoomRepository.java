package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Booking_room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRoomRepository extends JpaRepository<Booking_room, String>
{
    List<Booking_room> findByIdUser_IdUser(String idUser);

    List<Booking_room> findByIdRoom_IdRs_IdRs(String idResort);

    @Query("SELECT b FROM Booking_room b JOIN FETCH b.idRoom r JOIN FETCH r.idRs WHERE b.idUser.idUser = :idUser")
    List<Booking_room> findByIdUser_IdUserWithRoomAndResort(String idUser);
}
