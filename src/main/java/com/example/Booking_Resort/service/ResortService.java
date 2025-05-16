package com.example.Booking_Resort.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.impl.UploadImageFile;
import com.example.Booking_Resort.mapper.ResortMapper;
import com.example.Booking_Resort.models.Image;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.User;
import com.example.Booking_Resort.repository.ImageRepository;
import com.example.Booking_Resort.repository.ResortRepository;
import com.example.Booking_Resort.repository.UserRepository;
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

    // Hàm lấy danh sách resort
    public List<ResortResponse> getAllResort()
    {
        List<Resort> resorts = resortRepository.findAll();
        return resorts.stream().map(resort -> {
            Image image = imageRepository.findFirstByIdRs_IdRs(resort.getIdRs());
            return ResortResponse.builder()
                    .idRs(resort.getIdRs())
                    .name_rs(resort.getName_rs())
                    .location_rs(resort.getLocation_rs())
                    .describe_rs(resort.getDescribe_rs())
                    .image(image != null ? image.getUrl() : null)
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
    public ResortResponse getInfResort(String idresort)
    {
        Resort resort = resortRepository.findById(idresort).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );
        return resortMapper.toResortRespone(resort);
    }
}