package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.BookingRoomRequest;
import com.example.Booking_Resort.dto.request.BookingRoomUpdateRequest;
import com.example.Booking_Resort.dto.request.BookingServiceRequest;
import com.example.Booking_Resort.dto.response.BookingRoomRespone;
import com.example.Booking_Resort.dto.response.BookingServiceResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.BookingMapper;
import com.example.Booking_Resort.models.*;
import com.example.Booking_Resort.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService
{
    BookingRoomRepository bookingRoomRepository;
    BookingServiceRepository bookingServiceRepository;
    ServiceRSRepository serviceRSRepository;
    UserRepository userRepository;
    RoomRepository roomRepository;
    BookingMapper bookingMapper;

    // Hàm lưu phòng được đặt xuống csdl
    public BookingRoomRespone saveBookingRoom(BookingRoomRequest request) {
        User user = userRepository.findById(request.getId_user()).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Room room = roomRepository.findById(request.getId_room()).orElseThrow(
                () -> new ApiException(ErrorCode.ROOM_NOT_FOUND));

        Booking_room bookingRoom = bookingMapper.toBookingRoom(request);
        bookingRoom.setId_room(room);
        bookingRoom.setId_user(user);

        // Biến tính tổng tiền phòng + dịch vụ
        BigDecimal totalAmount = room.getPrice();

        bookingRoomRepository.save(bookingRoom);

        List<BookingServiceResponse> serviceResponses = new ArrayList<>();
        if (request.getServices() != null && !request.getServices().isEmpty()) {

            List<Booking_Service> bookingServices = new ArrayList<>();
            for (BookingServiceRequest serviceInfo : request.getServices()) {

                // Truy vấn dịch vụ từ cơ sở dữ liệu theo Id_sv
                ServiceRS serviceRS = serviceRSRepository.findById(serviceInfo.getId_sv())
                        .orElseThrow(() -> new ApiException(ErrorCode.SERVICE_NOT_FOUND));

                // Biến tiền của từng dịch vụ
                BigDecimal serviceTotal = serviceRS.getPrice().multiply(BigDecimal.valueOf(serviceInfo.getQuantity()));

                // Tạo Booking_Service và gán các thông tin
                Booking_Service bookingService = new Booking_Service();
                bookingService.setIdSV(serviceRS);
                bookingService.setIdUser(user);
                bookingService.setQuantity(serviceInfo.getQuantity());
                bookingService.setTotal_amount(serviceTotal);

                totalAmount = totalAmount.add(serviceTotal);

                // Thêm vào danh sách bookingServices
                bookingServices.add(bookingService);
            }
            // Lưu tất cả dịch vụ vào cơ sở dữ liệu
            bookingServiceRepository.saveAll(bookingServices);

        }
        bookingRoom.setTotal_amount(totalAmount);
        bookingRoom = bookingRoomRepository.save(bookingRoom);
        return bookingMapper.toBookingRespone(bookingRoom);
    }

    // Hàm lấy thông tin booking
    public List<Booking_room> getListBookingRoom()
    {
        return bookingRoomRepository.findAll();
    }

    // Hàm sửa đặt phòng
    @Transactional
    public BookingRoomRespone changeBookingRoom(BookingRoomUpdateRequest request, String idBookingRoom) {
        Booking_room bookingRoom = bookingRoomRepository.findById(idBookingRoom)
                .orElseThrow(() -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND));

        bookingMapper.updateBookingRoom(bookingRoom, request);

        // Biến tính tổng tiền lại từ đầu
        BigDecimal totalAmount = bookingRoom.getId_room().getPrice();

        // Xử lý danh sách dịch vụ mới (nếu có)
        if (request.getServices() != null && !request.getServices().isEmpty()) {
            bookingServiceRepository.deleteByIdUser(bookingRoom.getId_user());

            List<Booking_Service> bookingServices = new ArrayList<>();
            for (BookingServiceRequest serviceInfo : request.getServices()) {
                ServiceRS serviceRS = serviceRSRepository.findById(serviceInfo.getId_sv())
                        .orElseThrow(() -> new ApiException(ErrorCode.SERVICE_NOT_FOUND));

                BigDecimal serviceTotal = serviceRS.getPrice().multiply(BigDecimal.valueOf(serviceInfo.getQuantity()));

                Booking_Service bookingService = new Booking_Service();
                bookingService.setIdSV(serviceRS);
                bookingService.setIdUser(bookingRoom.getId_user());
                bookingService.setQuantity(serviceInfo.getQuantity());
                bookingService.setTotal_amount(serviceTotal);

                totalAmount = totalAmount.add(serviceTotal);
                bookingServices.add(bookingService);
            }

            bookingServiceRepository.saveAll(bookingServices);
        }

        bookingRoom.setTotal_amount(totalAmount);

        bookingRoom = bookingRoomRepository.save(bookingRoom);

        return bookingMapper.toBookingRespone(bookingRoom);
    }

    // Hàm xóa đặt phòng
    @Transactional
    public void deleteBookingRoom(String idBookingRoom)
    {
        Booking_room bookingRoom = bookingRoomRepository.findById(idBookingRoom).orElseThrow(
                () -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND)
        );
        bookingServiceRepository.deleteByIdUser(bookingRoom.getId_user());
        bookingRoomRepository.deleteById(idBookingRoom);
    }
}