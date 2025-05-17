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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // Hàm lấy danh sách yêu thích
    public List<FavoriteResortRespone> getFavoriteResorts(String idUser) {
        userRepository.findById(idUser).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        List<Favorite_Resort> favorites = favoriteResortRepository.findFavoriteResortsByUserId(idUser);

        List<FavoriteResortRespone> result = new ArrayList<>();
        for (Favorite_Resort fr : favorites) {
            Resort resort = fr.getId_rs();

            FavoriteResortRespone dto = new FavoriteResortRespone();
            dto.setResortId(resort.getIdRs());
            dto.setResortName(resort.getName_rs());

            String imageUrl = null;
            List<Image> images = resort.getImages();
            if (images != null && !images.isEmpty()) {
                Image singleImage = images.get(0);
                if (singleImage != null) {
                    imageUrl = singleImage.getUrl();
                }
            }
            dto.setImageUrl(imageUrl);
            dto.setCreated_at(fr.getCreated_at()); // thời gian yêu thích
            result.add(dto);
        }
        return result;
    }

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

        var favoriteResortRespone = new FavoriteResortRespone();
        favoriteResortRespone.setResortId(resort.getIdRs());
        favoriteResortRespone.setResortName(resort.getName_rs());
        favoriteResortRespone.setCreated_at(LocalDateTime.now());

        return favoriteResortRespone;
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
