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
import java.util.Optional;

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
            Resort resort = fr.getIdResort();

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

    public boolean toggleFavoriteResort(FavoriteResortRequest request) {
        User user = userRepository.findById(request.getId_user()).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );

        Resort resort = resortRepository.findById(request.getId_rs()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        FavoriteResortKey key = new FavoriteResortKey();
        key.setId_user(request.getId_user());
        key.setId_rs(request.getId_rs());

        Optional<Favorite_Resort> existingFavorite = favoriteResortRepository.findById(key);

        // Nếu thích rồi thì xóa
        if (existingFavorite.isPresent()) {
            favoriteResortRepository.deleteById(key);
            return false; // Đã xóa
        } else { // Nếu chưa thích thì tạo
            Favorite_Resort favoriteResort = new Favorite_Resort();
            favoriteResort.setId(key);
            favoriteResort.setIdUser(user);
            favoriteResort.setIdResort(resort);
            favoriteResortRepository.save(favoriteResort);
            return true;
        }
    }
}
