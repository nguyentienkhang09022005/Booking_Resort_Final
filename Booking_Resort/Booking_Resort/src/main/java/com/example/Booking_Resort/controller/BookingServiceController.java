package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.models.Booking_Service;
import com.example.Booking_Resort.service.BookingServiceService;
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
@RequestMapping("/api/booking_service")
public class BookingServiceController
{
    BookingServiceService bookingServiceService;

    // Endpoint lấy danh sách dịch vụ đã đặt
    @GetMapping("/list_bookingservice")
    public List<Booking_Service> getAllBookingService()
    {
        return this.bookingServiceService.getAllBookingServices();
    }
}
