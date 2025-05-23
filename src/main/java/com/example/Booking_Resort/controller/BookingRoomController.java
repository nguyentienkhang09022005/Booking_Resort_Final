package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.BookingRoomRequest;
import com.example.Booking_Resort.dto.request.BookingRoomUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.BookingRoomRespone;
import com.example.Booking_Resort.service.BookingService;
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
@RequestMapping("/api/booking_room")
public class BookingRoomController
{
    BookingService bookingService;

    // Endpoint lấy danh sách phòng được đặt
    @GetMapping("/list_bookingroom/{idUser}")
    public ApiRespone<List<BookingRoomRespone>> getAllBookingRoom(@PathVariable String idUser)
    {
        return ApiRespone.<List<BookingRoomRespone>>builder()
                .data(bookingService.getListBookingRoom(idUser))
                .build();
    }

    // Endpoint lấy thông tin phòng được đặt
    @GetMapping("/inf_bookingroom/{idBookingRoom}")
    public ApiRespone<BookingRoomRespone> infBookingRoom(@PathVariable String idBookingRoom)
    {
        return ApiRespone.<BookingRoomRespone>builder()
                .data(bookingService.getInfBookingRoom(idBookingRoom))
                .build();
    }

    // Endpoint lấy danh sách đặt phòng của resort
    @GetMapping("/list_bookingroom_resort/{idResort}")
    public ApiRespone<List<BookingRoomRespone>> getAllBookingRoomOfResort(@PathVariable String idResort)
    {
        return ApiRespone.<List<BookingRoomRespone>>builder()
                .data(bookingService.getListBookingRoomOfResort(idResort))
                .build();
    }

    // Endpoint đặt phòng
    @PostMapping("/create_bookingroom")
    public ApiRespone<BookingRoomRespone> createBookingRoom(@RequestBody BookingRoomRequest request)
    {
        return ApiRespone.<BookingRoomRespone>builder()
                .message("successful creation")
                .data(bookingService.saveBookingRoom(request))
                .build();
    }

    // Endpoint sửa đặt phòng
    @PutMapping("/change_bookingroom/{idBookingRoom}")
    public ApiRespone<BookingRoomRespone> changeBookingRoom(@RequestBody BookingRoomUpdateRequest request,
                                                            @PathVariable String idBookingRoom)
    {
        return ApiRespone.<BookingRoomRespone>builder()
                .message("successful updating")
                .data(bookingService.changeBookingRoom(request, idBookingRoom))
                .build();
    }

    // Endpoint xóa đặt phòng
    @DeleteMapping("delete_bookingroom/{idBookingRoom}")
    public ApiRespone<String> deleteBookingroom(@PathVariable String idBookingRoom)
    {
        bookingService.deleteBookingRoom(idBookingRoom);
        return ApiRespone.<String>builder()
                .message("successful deletion")
                .build();
    }
}
