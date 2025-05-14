package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.TypeRoomRequest;
import com.example.Booking_Resort.dto.response.TypeRoomRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.TypeRoomMapper;
import com.example.Booking_Resort.models.Type_Room;
import com.example.Booking_Resort.repository.TypeRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeRoomService
{
    TypeRoomRepository typeRoomRepository;
    TypeRoomMapper typeRoomMapper;

    // Hàm lấy danh sách loại phòng
    public List<Type_Room> getAllTypeRoom()
    {
        return this.typeRoomRepository.findAll();
    }

    // Hàm thêm loại phòng
    public TypeRoomRespone saveTypeRoom(TypeRoomRequest request)
    {
        Type_Room typeRoom = typeRoomMapper.toTypeRoom(request);
        return typeRoomMapper.toTypeRoomResponse(typeRoomRepository.save(typeRoom));
    }

    // Hàm sửa loại phòng
    public TypeRoomRespone updateTypeRoom(TypeRoomRequest request, String idTypeRoom)
    {
        Type_Room typeRoom = typeRoomRepository.findById(idTypeRoom).orElseThrow(
                () -> new ApiException(ErrorCode.TYPE_NOT_FOUND)
        );

        typeRoomMapper.updateTypeRoom(typeRoom, request);
        return typeRoomMapper.toTypeRoomResponse(typeRoomRepository.save(typeRoom));
    }

    // Hàm xóa loại phòng
    public void deleteTypeRoom(String idTypeRoom)
    {
        Type_Room typeRoom = typeRoomRepository.findById(idTypeRoom).orElseThrow(
                () -> new ApiException(ErrorCode.TYPE_NOT_FOUND)
        );
        typeRoomRepository.delete(typeRoom);
    }
}
