package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.TypeRoomRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.TypeRoomRespone;
import com.example.Booking_Resort.models.Type_Room;
import com.example.Booking_Resort.service.TypeRoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping({"/api/typeroom"})
public class TypeRoomController {

    TypeRoomService typeRoomService;

    // Endpoint lấy danh sách loại phòng
    @GetMapping("/list_typeroom")
    public ApiRespone<List<Type_Room>> getAllTypeRoom()
    {
        return ApiRespone.<List<Type_Room>>builder()
                .data(typeRoomService.getAllTypeRoom())
                .build();
    }

    // Endpoint tạo loại phòng mới
    @PostMapping("/create_typeroom")
    public ApiRespone<TypeRoomRespone> createTypeRoom(@RequestBody TypeRoomRequest request)
    {
        return ApiRespone.<TypeRoomRespone>builder()
                .message("successful creation")
                .data(typeRoomService.saveTypeRoom(request))
                .build();
    }

    // Endpoint thay đổi thông tin loại phòng
    @PutMapping("/update/{idTypeRoom}")
    public ApiRespone<TypeRoomRespone> updateRoom(@RequestBody TypeRoomRequest request,@PathVariable String idTypeRoom)
    {
        return ApiRespone.<TypeRoomRespone>builder()
                .message("successful update")
                .data(typeRoomService.updateTypeRoom(request, idTypeRoom))
                .build();
    }

    // Endpoint xóa loại phòng
    @DeleteMapping("/delete/{idTypeRoom}")
    public ApiRespone<String> deleteTypeRoom(@PathVariable ("idTypeRoom") String idTypeRoom)
    {
        typeRoomService.deleteTypeRoom(idTypeRoom);
        return ApiRespone.<String>builder()
                .message("successful deletion")
                .build();
    }
}