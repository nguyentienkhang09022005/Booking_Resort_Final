package com.example.Booking_Resort.controller;

import java.util.List;

import com.example.Booking_Resort.dto.request.RoomCreationRequest;
import com.example.Booking_Resort.dto.request.RoomUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.RoomRespone;
import com.example.Booking_Resort.service.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping({"/api/room"})
public class RoomController {

    RoomService roomService;

    // Endpoint lấy danh sách phòng
    @GetMapping("/list_room/{idResort}")
    public ApiRespone<List<RoomRespone>> getAllRoom(@PathVariable String idResort)
    {
        return ApiRespone.<List<RoomRespone>>builder()
                .data(roomService.getAllRoom(idResort))
                .build();
    }

    // Endpoint lấy thông tin chi tiết của phòng
    @GetMapping("inf_room/{idRoom}")
    public ApiRespone<RoomRespone> getInfRoom(@PathVariable ("idRoom") String idRoom)
    {
        return ApiRespone.<RoomRespone>builder()
                .data(roomService.getInfRoom(idRoom))
                .build();
    }

    // Endpoint tạo phòng mới
    @PostMapping(value = "/create_room", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<RoomRespone> createRoom(@RequestPart("request") RoomCreationRequest request,
                                              @RequestPart(value = "file", required = false) MultipartFile file)
    {
        request.setImage(file);
        return ApiRespone.<RoomRespone>builder()
                .message("successful creation")
                .data(roomService.saveRoom(request))
                .build();
    }

    // Endpoint thay đổi thông tin phòng
    @PutMapping("/update/{idRoom}")
    public ApiRespone<RoomRespone> updateRoom(@PathVariable ("idRoom") String idRoom,
                                              @RequestPart("request") RoomUpdateRequest request,
                                              @RequestPart(value = "file", required = false) MultipartFile file)
    {
        request.setImage(file);
        return ApiRespone.<RoomRespone>builder()
                .message("successful update")
                .data(roomService.updateRoom(idRoom, request))
                .build();
    }

    // Endpoint xóa phòng
    @DeleteMapping("/delete/{idRoom}")
    public ApiRespone<String> deleteRoom(@PathVariable ("idRoom") String idRoom)
    {
        roomService.deleteRoom(idRoom);
        return ApiRespone.<String>builder()
                .message("successful deletion")
                .build();
    }
}