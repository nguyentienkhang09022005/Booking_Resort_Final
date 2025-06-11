package com.example.Booking_Resort.controller;

import java.util.List;

import com.example.Booking_Resort.dto.request.ResortCreationRequest;
import com.example.Booking_Resort.dto.request.ResortUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.InfResortResponse;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.service.ResortService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping({"/api/resort"})
public class ResortController {

    ResortService resortService;

    // Endpoint lấy danh sách resort
    @GetMapping("/list_resort/{idUser}")
    public ApiRespone<List<ResortResponse>> getAllResort(@PathVariable String idUser)
    {
        return ApiRespone.<List<ResortResponse>>builder()
                .data(resortService.getAllResort(idUser))
                .build();
    }

    // Endpoint lấy danh sách resort đã tạo
    @GetMapping("/list_resort_created/{idOwner}")
    public ApiRespone<List<ResortResponse>> getAllResortCreated(@PathVariable String idOwner)
    {
        return ApiRespone.<List<ResortResponse>>builder()
                .data(resortService.getInfResorCreated(idOwner))
                .build();
    }

    // Endpont lấy thông tin resort
    @GetMapping("/{idResort}/{idUser}")
    public ApiRespone<InfResortResponse> getInfResort(@PathVariable String idResort, @PathVariable String idUser)
    {
        return ApiRespone.<InfResortResponse>builder()
                .message("successful get information of resort")
                .data(resortService.getInfResort(idResort, idUser))
                .build();
    }

    // Endpoint tạo resort
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<ResortResponse> createResort(@RequestPart("request") ResortCreationRequest request,
                                                   @RequestPart(value = "file", required = false) MultipartFile file)
    {
        request.setImage(file);
        return ApiRespone.<ResortResponse>builder()
                .message("successful creation")
                .data(resortService.saveResort(request))
                .build();
    }

    // Endpoint xóa resrot qua ID
    @DeleteMapping("/delete/{resortID}")
    public ApiRespone<String> deleteResort(@PathVariable("resortID") String resortID)
    {
        resortService.deleteResort(resortID);
        return ApiRespone.<String>builder()
                .message("successful deletion")
                .build();
    }

    // Endpoint thay đổi thông tin resort
    @PutMapping("/update/{resortID}")
    public ApiRespone<ResortResponse> updateResort(@PathVariable ("resortID") String resortID,
                                                   @RequestPart("request") ResortUpdateRequest request,
                                                   @RequestPart(value = "file", required = false) MultipartFile file)
    {
        request.setImage(file);
        return ApiRespone.<ResortResponse>builder()
                .message("successful update")
                .data(resortService.updateRosort(resortID, request))
                .build();
    }
}
