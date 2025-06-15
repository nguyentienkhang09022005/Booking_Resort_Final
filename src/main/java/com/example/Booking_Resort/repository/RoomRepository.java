package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByIdRs_IdRs(String idRs);

    // Lấy tất cả phòng theo danh sách idRs
    List<Room> findByIdRs_IdRsIn(List<String> idRs);
}
