package com.example.Booking_Resort.service;

import com.example.Booking_Resort.models.Booking_Service;
import com.example.Booking_Resort.repository.BookingServiceRepository;
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
public class BookingServiceService
{
    BookingServiceRepository bookingServiceRepository;

    // Hàm lấy danh sách dịch vụ đã đặt
    public List<Booking_Service> getAllBookingServices()
    {
        return this.bookingServiceRepository.findAll();
    }
}
