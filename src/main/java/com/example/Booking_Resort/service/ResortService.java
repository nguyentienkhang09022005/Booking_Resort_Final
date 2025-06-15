package com.example.Booking_Resort.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.EvaluateRespone;
import com.example.Booking_Resort.dto.response.InfResortResponse;
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
    public List<ResortResponse> getAllResort(String idUser) {
        List<Resort> resorts = resortRepository.findAll();
        List<String> resortIds = resorts.stream().map(Resort::getIdRs).collect(Collectors.toList());

        // Truy vấn batch
        List<String> favoriteResortIds = favoriteResortRepository.findFavoriteResortIdsByUserId(idUser);
        List<Image> images = imageRepository.findByIdRs_IdRsIn(resortIds);
        List<Room> rooms = roomRepository.findByIdRs_IdRsIn(resortIds);
        List<Evaluate> evaluates = evaluateRepository.findByIdRs_IdRsIn(resortIds);
        List<Object[]> avgRatings = evaluateRepository.getAverageRatingsByResortIds(resortIds);

        // Map dữ liệu sang HashMap để dễ lấy
        Map<String, String> resortImageMap = images.stream()
                .collect(Collectors.toMap(img -> img.getIdRs().getIdRs(), Image::getUrl, (a, b) -> a)); // Lấy ảnh đầu tiên

        Map<String, List<Room>> roomsMap = rooms.stream()
                .collect(Collectors.groupingBy(r -> r.getIdRs().getIdRs()));

        Map<String, List<Evaluate>> evaluateMap = evaluates.stream()
                .collect(Collectors.groupingBy(e -> e.getIdRs().getIdRs()));

        Map<String, Double> avgRatingMap = avgRatings.stream()
                .collect(Collectors.toMap(o -> (String) o[0], o -> Math.round(((Double) o[1]) * 10.0) / 10.0));

        // Lấy ảnh phòng
        List<String> roomIds = rooms.stream().map(Room::getIdRoom).collect(Collectors.toList());
        List<Image> roomImages = imageRepository.findByIdRoom_IdRoomIn(roomIds);
        Map<String, String> roomImageMap = roomImages.stream()
                .collect(Collectors.toMap(img -> img.getIdRoom().getIdRoom(), Image::getUrl, (a, b) -> a));

        return resorts.stream().map(resort -> {
            List<Room> resortRooms = roomsMap.getOrDefault(resort.getIdRs(), List.of());
            List<Evaluate> resortEvaluates = evaluateMap.getOrDefault(resort.getIdRs(), List.of());

            List<RoomRespone> roomResponses = resortRooms.stream().map(room -> RoomRespone.builder()
                    .idRoom(room.getIdRoom())
                    .name_room(room.getName_room())
                    .type_room(room.getId_type().getNameType())
                    .price(room.getPrice())
                    .status(room.getStatus())
                    .describe_room(room.getDescribe_room())
                    .image(roomImageMap.get(room.getIdRoom()))
                    .build()
            ).toList();

            List<EvaluateRespone> evaluationResponses = resortEvaluates.stream().map(evaluate -> EvaluateRespone.builder()
                    .idEvaluate(evaluate.getId_evaluate())
                    .user_comment(evaluate.getUser_comment())
                    .star_rating(evaluate.getStar_rating())
                    .create_date(evaluate.getCreate_date())
                    .build()
            ).toList();

            return ResortResponse.builder()
                    .idRs(resort.getIdRs())
                    .name_rs(resort.getName_rs())
                    .location_rs(resort.getLocation_rs())
                    .describe_rs(resort.getDescribe_rs())
                    .image(resortImageMap.get(resort.getIdRs()))
                    .star(avgRatingMap.getOrDefault(resort.getIdRs(), 0.0))
                    .favorite(favoriteResortIds.contains(resort.getIdRs()))
                    .rooms(roomResponses)
                    .evaluates(evaluationResponses)
                    .build();
        }).collect(Collectors.toList());
    }


    // Hàm lưu resort xuống csdl
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
    public void deleteResort(String idresort)
    {
        resortRepository.findById(idresort).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND));
        resortRepository.deleteById(idresort);
    }

    // Hàm thay đổi thông tin resort
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
    public InfResortResponse getInfResort(String idResort, String idUser) {
        Resort resort = resortRepository.findById(idResort)
                .orElseThrow(() -> new ApiException(ErrorCode.RESORT_NOT_FOUND));

        userRepository.findById(idUser)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        // Truy vấn batch
        Double avgRating = evaluateRepository.getAverageStartRatingByResort(idResort);
        double star = avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0;

        List<Room> rooms = roomRepository.findByIdRs_IdRs(idResort);
        List<Evaluate> evaluates = evaluateRepository.findByIdRs_IdRs(idResort);
        List<String> roomIds = rooms.stream().map(Room::getIdRoom).collect(Collectors.toList());
        List<String> userIds = evaluates.stream().map(e -> e.getIdUser().getIdUser()).distinct().collect(Collectors.toList());

        List<Image> roomImages = imageRepository.findByIdRoom_IdRoomIn(roomIds);
        Map<String, String> roomImageMap = roomImages.stream()
                .collect(Collectors.toMap(img -> img.getIdRoom().getIdRoom(), Image::getUrl, (a, b) -> a));

        List<Image> userAvatars = imageRepository.findByIdUser_IdUserIn(userIds);
        Map<String, String> avatarMap = userAvatars.stream()
                .collect(Collectors.toMap(img -> img.getIdUser().getIdUser(), Image::getUrl, (a, b) -> a));

        // Build responses
        List<RoomRespone> roomResponses = rooms.stream().map(room -> RoomRespone.builder()
                .idRoom(room.getIdRoom())
                .name_room(room.getName_room())
                .type_room(room.getId_type().getNameType())
                .price(room.getPrice())
                .status(room.getStatus())
                .describe_room(room.getDescribe_room())
                .image(roomImageMap.get(room.getIdRoom()))
                .build()
        ).toList();

        List<InfResortResponse.EvaluateInfResortResponse> evaluationResponses = evaluates.stream().map(evaluate -> {
            User user = evaluate.getIdUser();
            return InfResortResponse.EvaluateInfResortResponse.builder()
                    .idEvaluate(evaluate.getId_evaluate())
                    .idUser(user.getIdUser())
                    .nameuser(user.getNameuser())
                    .avatar(avatarMap.get(user.getIdUser()))
                    .user_comment(evaluate.getUser_comment())
                    .star_rating(evaluate.getStar_rating())
                    .create_date(evaluate.getCreate_date())
                    .build();
        }).toList();

        Image resortImage = imageRepository.findFirstByIdRs_IdRs(resort.getIdRs());
        String resortImageUrl = resortImage != null ? resortImage.getUrl() : null;

        var resortResponse = resortMapper.toResortInfRespone(resort);
        resortResponse.setImage(resortImageUrl);
        resortResponse.setRooms(roomResponses);
        resortResponse.setEvaluates(evaluationResponses);
        resortResponse.setStar(star);

        boolean isFavorite = favoriteResortRepository.existsByIdUser_IdUserAndIdResort_IdRs(idUser, idResort);
        resortResponse.setFavorite(isFavorite);

        return resortResponse;
    }


    // Hàm lấy danh sách resort đã tạo
    public List<ResortResponse> getInfResorCreated(String idOwner) {
        User owner = userRepository.findById(idOwner)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        List<Resort> resorts = resortRepository.findByIdOwner_IdUser(owner.getIdUser());
        List<String> resortIds = resorts.stream().map(Resort::getIdRs).toList();

        List<String> favoriteResortIds = favoriteResortRepository.findFavoriteResortIdsByUserId(idOwner);
        List<Image> resortImages = imageRepository.findByIdRs_IdRsIn(resortIds);
        Map<String, String> resortImageMap = resortImages.stream()
                .collect(Collectors.toMap(img -> img.getIdRs().getIdRs(), Image::getUrl, (a, b) -> a));

        List<Room> rooms = roomRepository.findByIdRs_IdRsIn(resortIds);
        List<String> roomIds = rooms.stream().map(Room::getIdRoom).toList();
        List<Image> roomImages = imageRepository.findByIdRoom_IdRoomIn(roomIds);
        Map<String, String> roomImageMap = roomImages.stream()
                .collect(Collectors.toMap(img -> img.getIdRoom().getIdRoom(), Image::getUrl, (a, b) -> a));

        Map<String, List<Room>> roomByResort = rooms.stream()
                .collect(Collectors.groupingBy(r -> r.getIdRs().getIdRs()));

        List<Evaluate> evaluates = evaluateRepository.findByIdRs_IdRsIn(resortIds);
        Map<String, List<Evaluate>> evaluateByResort = evaluates.stream()
                .collect(Collectors.groupingBy(e -> e.getIdRs().getIdRs()));

        List<Object[]> avgRatings = evaluateRepository.getAverageRatingsByResortIds(resortIds);
        Map<String, Double> avgRatingMap = avgRatings.stream()
                .collect(Collectors.toMap(o -> (String) o[0], o -> Math.round(((Double) o[1]) * 10.0) / 10.0));

        return resorts.stream().map(resort -> {
            List<RoomRespone> roomResponses = roomByResort.getOrDefault(resort.getIdRs(), List.of()).stream().map(room -> RoomRespone.builder()
                    .idRoom(room.getIdRoom())
                    .name_room(room.getName_room())
                    .type_room(room.getId_type().getNameType())
                    .price(room.getPrice())
                    .status(room.getStatus())
                    .describe_room(room.getDescribe_room())
                    .image(roomImageMap.get(room.getIdRoom()))
                    .build()).toList();

            List<EvaluateRespone> evaluationResponses = evaluateByResort.getOrDefault(resort.getIdRs(), List.of()).stream().map(e -> EvaluateRespone.builder()
                    .idEvaluate(e.getId_evaluate())
                    .user_comment(e.getUser_comment())
                    .star_rating(e.getStar_rating())
                    .create_date(e.getCreate_date())
                    .build()).toList();

            return ResortResponse.builder()
                    .idRs(resort.getIdRs())
                    .name_rs(resort.getName_rs())
                    .location_rs(resort.getLocation_rs())
                    .describe_rs(resort.getDescribe_rs())
                    .image(resortImageMap.get(resort.getIdRs()))
                    .star(avgRatingMap.getOrDefault(resort.getIdRs(), 0.0))
                    .favorite(favoriteResortIds.contains(resort.getIdRs()))
                    .rooms(roomResponses)
                    .evaluates(evaluationResponses)
                    .build();
        }).toList();
    }
}