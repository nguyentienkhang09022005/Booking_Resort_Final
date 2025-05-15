package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.FavoriteResortRequest;
import com.example.Booking_Resort.dto.request.PaymentCreationRequest;
import com.example.Booking_Resort.dto.request.PaymentUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.FavoriteResortRespone;
import com.example.Booking_Resort.dto.response.PaymentRespone;
import com.example.Booking_Resort.models.Payment;
import com.example.Booking_Resort.service.FavoriteResortService;
import com.example.Booking_Resort.service.PaymentService;
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
@RequestMapping("/api/favorite_resort")
public class FavoriteResortController
{
    FavoriteResortService favoriteResortService;

    // Endpoint tạo yêu thích resort
    @PostMapping("/create_favorite")
    public ApiRespone<FavoriteResortRespone> createFavorite(@RequestBody FavoriteResortRequest request)
    {
        return ApiRespone.<FavoriteResortRespone>builder()
                .message("successful creation")
                .data(favoriteResortService.saveFavoriteResort(request))
                .build();
    }

    // Endpoint xóa yêu thích
    @DeleteMapping("/delete_favorite/{idUser}/{idResort}")
    public ApiRespone<String> deletePayment(@PathVariable String idUser, @PathVariable String idResort)
    {
        favoriteResortService.deleteFavoriteResort(idUser, idResort);
        return ApiRespone.<String>builder()
                .message("successful delete payment")
                .build();
    }
}