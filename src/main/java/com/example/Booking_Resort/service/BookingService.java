package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.BookingRoomRequest;
import com.example.Booking_Resort.dto.request.BookingRoomUpdateRequest;
import com.example.Booking_Resort.dto.request.BookingServiceRequest;
import com.example.Booking_Resort.dto.response.BookingRoomRespone;
import com.example.Booking_Resort.dto.response.BookingServiceResponse;
import com.example.Booking_Resort.dto.response.ResortForBookingResponse;
import com.example.Booking_Resort.dto.response.RoomForBookingRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.BookingMapper;
import com.example.Booking_Resort.mapper.ResortMapper;
import com.example.Booking_Resort.mapper.RoomMapper;
import com.example.Booking_Resort.models.*;
import com.example.Booking_Resort.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService
{
    BookingRoomRepository bookingRoomRepository;
    BookingServiceRepository bookingServiceRepository;
    ServiceRSRepository serviceRSRepository;
    ResortRepository resortRepository;
    UserRepository userRepository;
    RoomRepository roomRepository;
    BookingMapper bookingMapper;
    MonthlyReportRepository monthlyReportRepository;
    ResortMapper resortMapper;
    RoomMapper roomMapper;

    private String getFirstImageUrl(List<Image> images) {
        return (images != null && !images.isEmpty()) ? images.get(0).getUrl() : null;
    }

    // Hàm lấy danh sách booking
    public List<BookingRoomRespone> getListBookingRoom(String idUser) {
        List<Booking_room> bookingRooms = bookingRoomRepository.findByIdUser_IdUser(idUser);
        if (bookingRooms.isEmpty()) {
            throw new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND);
        }

        return bookingRooms.stream().map(bookingRoom -> {
            // Lấy dịch vụ theo bookingRoom hiện tại
            List<Booking_Service> services = bookingServiceRepository.findByIdBr_IdBr(bookingRoom.getIdBr());

            List<BookingServiceResponse> serviceDtos = services.stream()
                    .map(service -> BookingServiceResponse.builder()
                            .idSV(service.getIdSV().getId_sv())
                            .nameService(service.getIdSV().getName_sv())
                            .quantity(service.getQuantity())
                            .total_amount(service.getTotal_amount())
                            .build())
                    .collect(Collectors.toList());

            Resort resort = bookingRoom.getIdRoom().getIdRs();
            Room room = bookingRoom.getIdRoom();

            return BookingRoomRespone.builder()
                    .idBr(bookingRoom.getIdBr())
                    .checkinday(bookingRoom.getCheckinday())
                    .checkoutday(bookingRoom.getCheckoutday())
                    .total_amount(bookingRoom.getTotal_amount())
                    .status(bookingRoom.getStatus())
                    .resortResponse(ResortForBookingResponse.builder()
                            .name_rs(resort.getName_rs())
                            .location_rs(resort.getLocation_rs())
                            .image(getFirstImageUrl(resort.getImages()))
                            .build())
                    .roomResponse(RoomForBookingRespone.builder()
                            .name_room(room.getName_room())
                            .type_room(room.getId_type().getNameType())
                            .price(room.getPrice())
                            .image(getFirstImageUrl(room.getImages()))
                            .build())
                    .services(serviceDtos)
                    .build();
        }).collect(Collectors.toList());
    }

    // Hàm lấy thông tin chi tiết đặt phòng
    public BookingRoomRespone getInfBookingRoom(String idBookingRoom) {
        Booking_room bookingRoom = bookingRoomRepository.findById(idBookingRoom).orElseThrow(
                () -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND)
        );

        List<Booking_Service> services = bookingServiceRepository.findByIdBr_IdBr(idBookingRoom);

        List<BookingServiceResponse> listService = services.stream()
                .map(service -> BookingServiceResponse.builder()
                        .idSV(service.getIdSV().getId_sv())
                        .nameService(service.getIdSV().getName_sv())
                        .quantity(service.getQuantity())
                        .total_amount(service.getTotal_amount())
                        .build())
                .collect(Collectors.toList());

        Resort resort = bookingRoom.getIdRoom().getIdRs();
        Room room = bookingRoom.getIdRoom();

        return BookingRoomRespone.builder()
                .idBr(bookingRoom.getIdBr())
                .idResort(resort.getIdRs())
                .checkinday(bookingRoom.getCheckinday())
                .checkoutday(bookingRoom.getCheckoutday())
                .total_amount(bookingRoom.getTotal_amount())
                .status(bookingRoom.getStatus())
                .resortResponse(ResortForBookingResponse.builder()
                        .name_rs(resort.getName_rs())
                        .location_rs(resort.getLocation_rs())
                        .image(getFirstImageUrl(resort.getImages()))
                        .build())
                .roomResponse(RoomForBookingRespone.builder()
                        .name_room(room.getName_room())
                        .type_room(room.getId_type().getNameType())
                        .price(room.getPrice())
                        .image(getFirstImageUrl(room.getImages()))
                        .build())
                .services(listService)
                .build();
    }


    // Hàm lấy danh sách đặt phòng của mỗi resort
    public List<BookingRoomRespone> getListBookingRoomOfResort(String idResort) {
        resortRepository.findById(idResort).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        List<Booking_room> listBookingRoom = bookingRoomRepository.findByIdRoom_IdRs_IdRs(idResort);

        return listBookingRoom.stream().map(bookingRoom -> {
            List<Booking_Service> services = bookingServiceRepository.findByIdBr_IdBr(bookingRoom.getIdBr());

            List<BookingServiceResponse> serviceResponses = services.stream()
                    .map(service -> BookingServiceResponse.builder()
                            .idSV(service.getIdSV().getId_sv())
                            .nameService(service.getIdSV().getName_sv())
                            .quantity(service.getQuantity())
                            .total_amount(service.getTotal_amount())
                            .build())
                    .collect(Collectors.toList());

            Resort resort = bookingRoom.getIdRoom().getIdRs();
            Room room = bookingRoom.getIdRoom();

            return BookingRoomRespone.builder()
                    .idBr(bookingRoom.getIdBr())
                    .checkinday(bookingRoom.getCheckinday())
                    .checkoutday(bookingRoom.getCheckoutday())
                    .total_amount(bookingRoom.getTotal_amount())
                    .status(bookingRoom.getStatus())
                    .resortResponse(ResortForBookingResponse.builder()
                            .name_rs(resort.getName_rs())
                            .location_rs(resort.getLocation_rs())
                            .image(getFirstImageUrl(resort.getImages()))
                            .build())
                    .roomResponse(RoomForBookingRespone.builder()
                            .name_room(room.getName_room())
                            .type_room(room.getId_type().getNameType())
                            .price(room.getPrice())
                            .image(getFirstImageUrl(room.getImages()))
                            .build())
                    .services(serviceResponses)
                    .build();
        }).collect(Collectors.toList());
    }


    @Transactional
    // Hàm lưu phòng được đặt xuống csdl
    public BookingRoomRespone saveBookingRoom(BookingRoomRequest request) {
        User user = userRepository.findById(request.getId_user()).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Room room = roomRepository.findById(request.getId_room()).orElseThrow(
                () -> new ApiException(ErrorCode.ROOM_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now().withNano(0);

        Booking_room bookingRoom = bookingMapper.toBookingRoom(request);
        bookingRoom.setIdRoom(room);
        bookingRoom.setIdUser(user);
        bookingRoom.setCreate_date(now);

        BigDecimal totalAmount = room.getPrice();

        bookingRoom = bookingRoomRepository.save(bookingRoom);

        List<BookingServiceResponse> serviceResponses = new ArrayList<>();
        if (request.getServices() != null && !request.getServices().isEmpty()) {
            List<Booking_Service> bookingServices = new ArrayList<>();
            for (BookingServiceRequest serviceInfo : request.getServices()) {

                if (serviceInfo == null || serviceInfo.getId_sv() == null || serviceInfo.getQuantity() == null) {
                    continue;
                }

                ServiceRS serviceRS = serviceRSRepository.findById(serviceInfo.getId_sv())
                        .orElseThrow(() -> new ApiException(ErrorCode.SERVICE_NOT_FOUND));

                BigDecimal serviceTotal = serviceRS.getPrice().multiply(BigDecimal.valueOf(serviceInfo.getQuantity()));

                Booking_Service bookingService = new Booking_Service();
                bookingService.setIdSV(serviceRS);
                bookingService.setIdUser(user);
                bookingService.setQuantity(serviceInfo.getQuantity());
                bookingService.setTotal_amount(serviceTotal);
                bookingService.setIdBr(bookingRoom);

                totalAmount = totalAmount.add(serviceTotal);

                bookingServices.add(bookingService);
            }
            bookingServiceRepository.saveAll(bookingServices);

            serviceResponses = bookingServices.stream()
                    .map(bs -> BookingServiceResponse.builder()
                            .idSV(bs.getIdSV().getId_sv())
                            .nameService(bs.getIdSV().getName_sv())
                            .quantity(bs.getQuantity())
                            .total_amount(bs.getTotal_amount())
                            .build())
                    .collect(Collectors.toList());
        }

        // Cập nhật tổng tiền và lưu bookingRoom LẦN HAI
        bookingRoom.setTotal_amount(totalAmount);
        bookingRoom = bookingRoomRepository.save(bookingRoom); // Lưu lại để cập nhật total_amount

        // ... (Phần còn lại của code của bạn)

        // Lấy tháng năm hiện tại
        int month = now.getMonthValue();
        int year = now.getYear();
        Resort resort = room.getIdRs();

        // Tạo báo cáo nếu chưa có
        Monthly_Report report = monthlyReportRepository
                .findByReportMonthAndReportYearAndIdResort(month, year, resort)
                .orElseGet(() -> {
                    Monthly_Report newReport = new Monthly_Report();
                    newReport.setReportMonth(month);
                    newReport.setReportYear(year);
                    newReport.setIdResort(resort);
                    newReport.setTotalRevenue(BigDecimal.ZERO);
                    newReport.setTotalExpense(BigDecimal.ZERO);
                    newReport.setNetProfit(BigDecimal.ZERO);
                    newReport.setDetails(new ArrayList<>());
                    return monthlyReportRepository.save(newReport);
                });

        // Tạo chi tiết báo cáo
        Detail_Report detail = new Detail_Report();
        detail.setIdReport(report);
        detail.setType("Thu");
        detail.setAmount(totalAmount);
        detail.setCreateDate(now);

        report.getDetails().add(detail);
        report.setTotalRevenue(report.getTotalRevenue().add(detail.getAmount()));
        report.setNetProfit(report.getTotalRevenue().subtract(report.getTotalExpense()));
        monthlyReportRepository.save(report);

        var bookingResponse = bookingMapper.toBookingRespone(bookingRoom);
        bookingResponse.setIdBr(bookingRoom.getIdBr());
        bookingResponse.setServices(serviceResponses);

        ResortForBookingResponse resortInfo = resortMapper.toResortForBookingResponse(room.getIdRs());
        resortInfo.setImage(getFirstImageUrl(room.getIdRs().getImages()));
        RoomForBookingRespone roomInfo = roomMapper.toRoomForBookingResponse(room);
        roomInfo.setType_room(room.getId_type().getNameType());
        roomInfo.setImage(getFirstImageUrl(room.getImages()));

        bookingResponse.setResortResponse(resortInfo);
        bookingResponse.setRoomResponse(roomInfo);

        return bookingResponse;
    }

    // Hàm sửa đặt phòng
    @Transactional
    public BookingRoomRespone changeBookingRoom(BookingRoomUpdateRequest request, String idBookingRoom) {
        // Lấy thông tin đặt phòng hiện tại
        Booking_room bookingRoom = bookingRoomRepository.findById(idBookingRoom)
                .orElseThrow(() -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND));

        // Lưu lại thông tin cũ
        BigDecimal oldAmount = bookingRoom.getTotal_amount();
        LocalDateTime bookingDate = bookingRoom.getCreate_date().withNano(0);
        Resort resort = bookingRoom.getIdRoom().getIdRs();
        int month = bookingDate.getMonthValue();
        int year = bookingDate.getYear();

        // Cập nhật thông tin đặt phòng từ request
        bookingMapper.updateBookingRoom(bookingRoom, request);

        // Xử lý lại danh sách dịch vụ
        BigDecimal roomPrice = bookingRoom.getIdRoom().getPrice();
        BigDecimal totalAmount = roomPrice;

        List<BookingServiceResponse> serviceResponses = new ArrayList<>();

        if (request.getServices() != null && !request.getServices().isEmpty()) {
            // Xóa dịch vụ cũ
            bookingServiceRepository.deleteByIdUser(bookingRoom.getIdUser());

            // Thêm danh sách dịch vụ mới
            List<Booking_Service> newServices = new ArrayList<>();
            for (BookingServiceRequest serviceReq : request.getServices()) {
                ServiceRS service = serviceRSRepository.findById(serviceReq.getId_sv())
                        .orElseThrow(() -> new ApiException(ErrorCode.SERVICE_NOT_FOUND));

                BigDecimal serviceAmount = service.getPrice().multiply(BigDecimal.valueOf(serviceReq.getQuantity()));

                Booking_Service bookingService = new Booking_Service();
                bookingService.setIdSV(service);
                bookingService.setIdUser(bookingRoom.getIdUser());
                bookingService.setQuantity(serviceReq.getQuantity());
                bookingService.setTotal_amount(serviceAmount);
                bookingService.setIdBr(bookingRoom);

                totalAmount = totalAmount.add(serviceAmount);
                newServices.add(bookingService);
            }
            bookingServiceRepository.saveAll(newServices);

            serviceResponses = newServices.stream()
                    .map(bs -> BookingServiceResponse.builder()
                            .idSV(bs.getIdSV().getId_sv())
                            .nameService(bs.getIdSV().getName_sv())
                            .quantity(bs.getQuantity())
                            .total_amount(bs.getTotal_amount())
                            .build())
                    .collect(Collectors.toList());
        }

        // Cập nhật lại tổng tiền và lưu bookingRoom
        bookingRoom.setTotal_amount(totalAmount);
        Booking_room updatedBooking = bookingRoomRepository.save(bookingRoom);

        // Tìm báo cáo tương ứng
        Monthly_Report report = monthlyReportRepository
                .findByReportMonthAndReportYearAndIdResort(month, year, resort)
                .orElseThrow(() -> new ApiException(ErrorCode.REPORT_NOT_FOUND));

        // Tìm Detail_Report tương ứng
        Detail_Report detailReport = report.getDetails().stream()
                .filter(detail -> detail.getType().equals("Thu")
                        && detail.getAmount().compareTo(oldAmount) == 0
                        && detail.getCreateDate().withNano(0).equals(bookingDate))
                .findFirst()
                .orElse(null); // Có thể null nếu chưa từng có, thì ta tạo mới

        if (detailReport != null) {
            detailReport.setAmount(totalAmount);
        } else {
            Detail_Report newDetail = new Detail_Report();
            newDetail.setIdReport(report);
            newDetail.setType("Thu");
            newDetail.setAmount(totalAmount.subtract(oldAmount));
            newDetail.setCreateDate(LocalDateTime.now().withNano(0));
            report.getDetails().add(newDetail);
        }

        // Cập nhật báo cáo tổng thu
        BigDecimal updatedRevenue = report.getTotalRevenue().subtract(oldAmount).add(totalAmount);
        report.setTotalRevenue(updatedRevenue);
        report.setNetProfit(updatedRevenue.subtract(report.getTotalExpense()));
        monthlyReportRepository.save(report);

        var bookingRespone = bookingMapper.toBookingRespone(updatedBooking);
        bookingRespone.setIdBr(updatedBooking.getIdBr());
        bookingRespone.setServices(serviceResponses);

        ResortForBookingResponse resortInfo = resortMapper.toResortForBookingResponse(updatedBooking.getIdRoom().getIdRs());
        resortInfo.setImage(getFirstImageUrl(updatedBooking.getIdRoom().getIdRs().getImages()));
        RoomForBookingRespone roomInfo = roomMapper.toRoomForBookingResponse(updatedBooking.getIdRoom());
        roomInfo.setImage(getFirstImageUrl(updatedBooking.getIdRoom().getImages()));
        roomInfo.setType_room(updatedBooking.getIdRoom().getId_type().getNameType());

        bookingRespone.setResortResponse(resortInfo);
        bookingRespone.setRoomResponse(roomInfo);
        return bookingRespone;
    }

    // Hàm xóa đặt phòng
    @Transactional
    public void deleteBookingRoom(String idBookingRoom)
    {
        Booking_room bookingRoom = bookingRoomRepository.findById(idBookingRoom).orElseThrow(
                () -> new ApiException(ErrorCode.BOOKING_ROOM_NOT_FOUND)
        );
        BigDecimal revenue = bookingRoom.getTotal_amount();
        LocalDateTime createDate = bookingRoom.getCreate_date().withNano(0);
        Resort resort = bookingRoom.getIdRoom().getIdRs();

        int month = createDate.getMonthValue();
        int year = createDate.getYear();

        // Xóa booking service
        bookingServiceRepository.deleteByIdUser(bookingRoom.getIdUser());

        // Tìm báo cáo
        Monthly_Report report = monthlyReportRepository
                .findByReportMonthAndReportYearAndIdResort(month, year, resort)
                .orElse(null);

        if (report != null) {
            // Xóa chi tiết report phù hợp
            report.getDetails().removeIf(detail ->
                    detail.getType().equals("Thu") &&
                            detail.getAmount().compareTo(revenue) == 0 &&
                            detail.getCreateDate().equals(createDate)
            );

            // Cập nhật lại tổng thu và lợi nhuận
            report.setTotalRevenue(report.getTotalRevenue().subtract(revenue));
            report.setNetProfit(report.getTotalRevenue().subtract(report.getTotalExpense()));

            monthlyReportRepository.save(report);
        }
        bookingRoomRepository.delete(bookingRoom);
    }
}