package com.example.Booking_Resort.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.EvaluateRespone;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.dto.response.RoomRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.impl.UploadImageFile;
import com.example.Booking_Resort.mapper.ResortMapper;
import com.example.Booking_Resort.models.*;
import com.example.Booking_Resort.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResortService {

    ResortRepository resortRepository;
    UserRepository userRepository;
    ResortMapper resortMapper;
    UploadImageFile uploadImageFile;
    ImageRepository imageRepository;
    EvaluateRepository evaluateRepository;
    FavoriteResortRepository favoriteResortRepository;
    RoomRepository roomRepository;

    // Hàm lấy danh sách resort
    public List<ResortResponse> getAllResort(String idUser)
    {
        List<Resort> resorts = resortRepository.findAll();
        List<String> favoriteResortIds = favoriteResortRepository.findFavoriteResortIdsByUserId(idUser);

        return resorts.stream().map(resort -> {
            Image image = imageRepository.findFirstByIdRs_IdRs(resort.getIdRs());
            Double avgRating = evaluateRepository.getAverageStartRatingByResort(resort.getIdRs());
            double star = avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0;

            // Lấy danh sách phòng thuộc resort
            List<Room> rooms = roomRepository.findByIdRs_IdRs(resort.getIdRs());

            // Lấy danh sách đánh giá thuộc resort
            List<Evaluate> evaluates = evaluateRepository.findByIdRs_IdRs(resort.getIdRs());

            List<EvaluateRespone> evaluationResponses = evaluates.stream().map(evaluate -> {
                User user = evaluate.getIdUser();
                return EvaluateRespone.builder()
                        .idEvaluate(evaluate.getId_evaluate())
                        .user_comment(evaluate.getUser_comment())
                        .star_rating(evaluate.getStar_rating())
                        .create_date(evaluate.getCreate_date())
                        .build();
            }).collect(Collectors.toList());

            // Map sang RoomRespone
            List<RoomRespone> roomResponses = rooms.stream().map(room -> {
                Image roomImage = imageRepository.findFirstByIdRoom_IdRoom(room.getIdRoom());

                return RoomRespone.builder()
                        .idRoom(room.getIdRoom())
                        .name_room(room.getName_room())
                        .type_room(room.getId_type().getNameType())
                        .price(room.getPrice())
                        .status(room.getStatus())
                        .describe_room(room.getDescribe_room())
                        .image(roomImage != null ? roomImage.getUrl() : null)
                        .build();
            }).collect(Collectors.toList());

            return ResortResponse.builder()
                    .idRs(resort.getIdRs())
                    .name_rs(resort.getName_rs())
                    .location_rs(resort.getLocation_rs())
                    .describe_rs(resort.getDescribe_rs())
                    .image(image != null ? image.getUrl() : null)
                    .star(star)
                    .favorite(favoriteResortIds.contains(resort.getIdRs()))
                    .rooms(roomResponses)
                    .evaluates(evaluationResponses)
                    .build();
        }).collect(Collectors.toList());
    }

    // Hàm lưu resort xuống csdl
//    @PreAuthorize("hasAuthority('CREATE_RESORT')")
    public ResortResponse saveResort(ResortCreationRequest request)
    {
        User user = userRepository.findById(request.getIdOwner()).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Resort resort = resortMapper.toResort(request);
        resort.setIdOwner(user);
        resortRepository.save(resort);

        String imageResortUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty())
        {
            try {
                imageResortUrl = uploadImageFile.uploadImage(request.getImage());

                Image image = new Image();
                image.setIdRs(resort);
                image.setUrl(imageResortUrl);

                imageRepository.save(image);
            } catch (IOException e) {
                log.error("Upload image failed", e);
                throw new ApiException(ErrorCode.UPLOAD_FAILED);
            }
        }

        ResortResponse resortResponse = resortMapper.toResortRespone(resort);
        resortResponse.setImage(imageResortUrl);
        resortResponse.setIdRs(resort.getIdRs());
        return resortResponse;
    }

    // Hàm xóa resort
//    @PreAuthorize("@resortRepository.findById(#idresort).get().idOwner.username == authentication.name " +
//                  "or hasRole('ROLE_ADMIN')") // chủ và admin mới được xóa
    public void deleteResort(String idresort)
    {
        resortRepository.findById(idresort).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND));
        resortRepository.deleteById(idresort);
    }

    // Hàm thay đổi thông tin resort
//    @PreAuthorize("@resortRepository.findById(#idresort).get().idOwner.account == authentication.name") // chủ mới được đổi
    @Transactional
    public ResortResponse updateRosort(String idresort, ResortUpdateRequest request)
    {
        Resort resort = resortRepository.findById(idresort).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND));

        resortMapper.updateResort(resort, request);
        String imageResortUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty())
        {
            try {
                imageRepository.deleteByIdRs(resort);
                imageResortUrl = uploadImageFile.uploadImage(request.getImage());

                Image image = new Image();
                image.setIdRs(resort);
                image.setUrl(imageResortUrl);

                imageRepository.save(image);
            } catch (IOException e) {
                log.error("Upload image failed", e);
                throw new ApiException(ErrorCode.UPLOAD_FAILED);
            }
        }

        ResortResponse resortResponse = resortMapper.toResortRespone(resort);
        resortResponse.setImage(imageResortUrl);
        return resortResponse;
    }

    // Hàm lấy thông tin resort
    public ResortResponse getInfResort(String idResort, String idUser)
    {
        Resort resort = resortRepository.findById(idResort).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );
        userRepository.findById(idUser).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );

        // Lấy danh sách phòng thuộc resort
        List<Room> rooms = roomRepository.findByIdRs_IdRs(resort.getIdRs());

        // Lấy danh sách đánh giá thuộc resort
        List<Evaluate> evaluates = evaluateRepository.findByIdRs_IdRs(resort.getIdRs());

        List<EvaluateRespone> evaluationResponses = evaluates.stream().map(evaluate -> {
            User user = evaluate.getIdUser();
            return EvaluateRespone.builder()
                    .idEvaluate(evaluate.getId_evaluate())
                    .user_comment(evaluate.getUser_comment())
                    .star_rating(evaluate.getStar_rating())
                    .create_date(evaluate.getCreate_date())
                    .build();
        }).collect(Collectors.toList());

        // Map sang RoomRespone
        List<RoomRespone> roomResponses = rooms.stream().map(room -> {
            Image roomImage = imageRepository.findFirstByIdRoom_IdRoom(room.getIdRoom());

            return RoomRespone.builder()
                    .idRoom(room.getIdRoom())
                    .name_room(room.getName_room())
                    .type_room(room.getId_type().getNameType())
                    .price(room.getPrice())
                    .status(room.getStatus())
                    .describe_room(room.getDescribe_room())
                    .image(roomImage != null ? roomImage.getUrl() : null)
                    .build();
        }).collect(Collectors.toList());

        var resortResponse = resortMapper.toResortRespone(resort);
        boolean isFavorite = favoriteResortRepository.existsByIdUser_IdUserAndIdResort_IdRs(idUser, idResort);
        resortResponse.setFavorite(isFavorite);
        resortResponse.setRooms(roomResponses);
        resortResponse.setEvaluates(evaluationResponses);
        resortResponse.setImage(resort.getImages().get(0).getUrl());
        return resortResponse;
    }

    // Hàm lấy danh sách resort đã tạo
    public List<ResortResponse> getInfResorCreated(String idOwner)
    {
        User owner = userRepository.findById(idOwner).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );
        List<Resort> resorts = resortRepository.findByIdOwner_IdUser(owner.getIdUser());

        List<String> favoriteResortIds = favoriteResortRepository.findFavoriteResortIdsByUserId(idOwner);

        return resorts.stream().map(resort -> {
            Image image = imageRepository.findFirstByIdRs_IdRs(resort.getIdRs());
            Double avgRating = evaluateRepository.getAverageStartRatingByResort(resort.getIdRs());
            double star = avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0;

            // Lấy danh sách phòng thuộc resort
            List<Room> rooms = roomRepository.findByIdRs_IdRs(resort.getIdRs());

            // Lấy danh sách đánh giá thuộc resort
            List<Evaluate> evaluates = evaluateRepository.findByIdRs_IdRs(resort.getIdRs());

            List<EvaluateRespone> evaluationResponses = evaluates.stream().map(evaluate -> {
                User user = evaluate.getIdUser();
                return EvaluateRespone.builder()
                        .idEvaluate(evaluate.getId_evaluate())
                        .user_comment(evaluate.getUser_comment())
                        .star_rating(evaluate.getStar_rating())
                        .create_date(evaluate.getCreate_date())
                        .build();
            }).collect(Collectors.toList());

            // Map sang RoomRespone
            List<RoomRespone> roomResponses = rooms.stream().map(room -> {
                Image roomImage = imageRepository.findFirstByIdRoom_IdRoom(room.getIdRoom());

                return RoomRespone.builder()
                        .idRoom(room.getIdRoom())
                        .name_room(room.getName_room())
                        .type_room(room.getId_type().getNameType())
                        .price(room.getPrice())
                        .status(room.getStatus())
                        .describe_room(room.getDescribe_room())
                        .image(roomImage != null ? roomImage.getUrl() : null)
                        .build();
            }).collect(Collectors.toList());

            return ResortResponse.builder()
                    .idRs(resort.getIdRs())
                    .name_rs(resort.getName_rs())
                    .location_rs(resort.getLocation_rs())
                    .describe_rs(resort.getDescribe_rs())
                    .image(image != null ? image.getUrl() : null)
                    .star(star)
                    .favorite(favoriteResortIds.contains(resort.getIdRs()))
                    .rooms(roomResponses)
                    .evaluates(evaluationResponses)
                    .build();
        }).collect(Collectors.toList());
    }
}