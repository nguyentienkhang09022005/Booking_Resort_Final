package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Image;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.Room;
import com.example.Booking_Resort.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    void deleteByIdUser(User user);
    void deleteByIdRoom(Room room);
    void deleteByIdRs(Resort resort);
    Image findFirstByIdRs_IdRs(String idRs);
    Image findFirstByIdRoom_IdRoom(String idRoom);
    Image findFirstByIdUser_IdUser(String idUser);

    // Lấy ảnh phòng theo danh sách idRoom
    List<Image> findByIdRoom_IdRoomIn(List<String> idRooms);

    // Lấy tất cả ảnh theo danh sách idRs
    List<Image> findByIdRs_IdRsIn(List<String> idRs);

    List<Image> findByIdUser_IdUserIn(List<String> userIds);

    List<Image> findByIdRs_IdRsIn(Set<String> idRs);
    List<Image> findByIdRoom_IdRoomIn(Set<String> idRooms);
}
