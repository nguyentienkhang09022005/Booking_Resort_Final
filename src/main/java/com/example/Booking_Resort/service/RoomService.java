package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.RoomCreationRequest;
import com.example.Booking_Resort.dto.request.RoomUpdateRequest;
import com.example.Booking_Resort.dto.response.RoomRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.impl.UploadImageFile;
import com.example.Booking_Resort.mapper.RoomMapper;
import com.example.Booking_Resort.models.Image;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.Room;
import com.example.Booking_Resort.models.Type_Room;
import com.example.Booking_Resort.repository.ImageRepository;
import com.example.Booking_Resort.repository.ResortRepository;
import com.example.Booking_Resort.repository.RoomRepository;
import com.example.Booking_Resort.repository.TypeRoomRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService
{
    ResortRepository resortRepository;
    RoomRepository roomRepository;
    TypeRoomRepository typeRoomRepository;
    RoomMapper roomMapper;
    UploadImageFile uploadImageFile;
    ImageRepository imageRepository;

    // Hàm lấy danh sách phòng
    public List<RoomRespone> getAllRoom() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(room -> {
            Image image = imageRepository.findFirstByIdRoom_IdRoom(room.getIdRoom());

            return RoomRespone.builder()
                    .idRoom(room.getIdRoom())
                    .name_room(room.getName_room())
                    .type_room(room.getId_type() != null ? room.getId_type().getNameType() : null) // lấy tên loại phòng
                    .price(room.getPrice())
                    .status(room.getStatus())
                    .describe_room(room.getDescribe_room())
                    .image(image != null ? image.getUrl() : null)
                    .build();
        }).collect(Collectors.toList());
    }


    // Hàm lưu phòng xuống csdl
//    @PreAuthorize("hasAuthority('CREATE_ROOM')")
    public RoomRespone saveRoom(RoomCreationRequest request)
    {
        Resort resort = resortRepository.findById(request.getId_rs()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        Type_Room typeRoom = typeRoomRepository.findById(request.getId_type()).orElseThrow(
                () -> new ApiException(ErrorCode.TYPE_NOT_FOUND)
        );

        Room room = roomMapper.toRoom(request);
        room.setId_rs(resort);
        room.setId_type(typeRoom);
        roomRepository.save(room);

        String imageRoomUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                imageRoomUrl = uploadImageFile.uploadImage(request.getImage());

                Image image = new Image();
                image.setIdRoom(room);
                image.setUrl(imageRoomUrl);

                imageRepository.save(image);
            } catch (IOException e) {
                log.error("Upload image failed", e);
                throw new ApiException(ErrorCode.UPLOAD_FAILED);
            }
        }
        RoomRespone roomRespone = roomMapper.toRoomRespone(room);
        roomRespone.setType_room(room.getId_type().getNameType());
        roomRespone.setImage(imageRoomUrl);
        return roomRespone;
    }

    // Hàm xóa phòng
//    @PreAuthorize("@roomRepository.findById(#idRoom).get().id_rs.idOwner.account == authentication.name " +
//                    "or hasRole('ROLE_ADMIN')") // chủ mới và admin được đổi
    public void deleteRoom(String idRoom)
    {
        roomRepository.findById(idRoom).orElseThrow(
                () -> new ApiException(ErrorCode.ROOM_NOT_FOUND)
        );
        roomRepository.deleteById(idRoom);
    }

    // Hàm thay đổi thông tin phòng
//    @PreAuthorize("@roomRepository.findById(#idRoom).get().id_rs.idOwner.account == authentication.name")
    @Transactional
    public RoomRespone updateRoom(String idRoom, RoomUpdateRequest request)
    {
        Room room = roomRepository.findById(idRoom).orElseThrow(
                () -> new ApiException(ErrorCode.ROOM_NOT_FOUND)
        );

        Type_Room typeRoom = typeRoomRepository.findById(request.getId_type()).orElseThrow(
                () -> new ApiException(ErrorCode.TYPE_NOT_FOUND)
        );

        roomMapper.updateRoom(room, request);
        room.setId_type(typeRoom);
        String imageRoomUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                imageRepository.deleteByIdRoom(room);

                imageRoomUrl = uploadImageFile.uploadImage(request.getImage());

                Image image = new Image();
                image.setIdRoom(room);
                image.setUrl(imageRoomUrl);

                imageRepository.save(image);
            } catch (IOException e) {
                log.error("Upload image failed", e);
                throw new ApiException(ErrorCode.UPLOAD_FAILED);
            }
        }
        RoomRespone roomRespone = roomMapper.toRoomRespone(room);
        roomRespone.setType_room(room.getId_type().getNameType());
        roomRespone.setImage(imageRoomUrl);
        return roomRespone;
    }

    // Hàm lấy thông tin phòng
    public RoomRespone getInfRoom(String idroom)
    {
        Room room = roomRepository.findById(idroom).orElseThrow(
                () -> new ApiException(ErrorCode.ROOM_NOT_FOUND)
        );
        return roomMapper.toRoomRespone(room);
    }
}
