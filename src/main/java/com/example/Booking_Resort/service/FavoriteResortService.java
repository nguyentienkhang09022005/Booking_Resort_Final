package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.FavoriteResortRequest;
import com.example.Booking_Resort.dto.response.FavoriteResortRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.FavoriteResortMapper;
import com.example.Booking_Resort.models.*;
import com.example.Booking_Resort.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteResortService
{
    FavoriteResortRepository favoriteResortRepository;
    UserRepository userRepository;
    ResortRepository resortRepository;
    FavoriteResortMapper favoriteResortMapper;

    // Hàm tạo yêu thích
    public FavoriteResortRespone saveFavoriteResort(FavoriteResortRequest request)
    {
        User user = userRepository.findById(request.getId_user()).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );

        Resort resort = resortRepository.findById(request.getId_rs()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        Favorite_Resort favoriteResort = new Favorite_Resort();
        FavoriteResortKey key = new FavoriteResortKey();
        key.setId_rs(request.getId_rs());
        key.setId_user(request.getId_user());

        favoriteResort.setId(key);
        favoriteResort.setId_user(user);
        favoriteResort.setId_rs(resort);
        favoriteResortRepository.save(favoriteResort);

        Favorite_Resort saved = favoriteResortRepository.findById(favoriteResort.getId())
                .orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));

        return favoriteResortMapper.toFavoriteResortResponse(saved);
    }

    // Hàm xóa yêu thích
    public void deleteFavoriteResort(String idUser, String idRs)
    {
        FavoriteResortKey key = new FavoriteResortKey();
        key.setId_user(idUser);
        key.setId_rs(idRs);

        favoriteResortRepository.deleteById(key);
    }
}
