package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.ServiceRSCreationRequest;
import com.example.Booking_Resort.dto.request.ServiceRSUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.ServiceRSResponse;
import com.example.Booking_Resort.models.ServiceRS;
import com.example.Booking_Resort.service.ServiceRSService;
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
@RequestMapping("/api/service")
public class ServiceRSController
{
    ServiceRSService serviceRSService;

    // Endpoint lấy danh sách service
    @GetMapping("/list_service")
    public ApiRespone<List<ServiceRS>> getAllService()
    {
        return ApiRespone.<List<ServiceRS>>builder()
                .data(serviceRSService.getAllService())
                .build();
    }

    // Endpoint tạo service mới
    @PostMapping("/create_service")
    public ApiRespone<ServiceRSResponse> createService(@RequestBody ServiceRSCreationRequest request)
    {
        return ApiRespone.<ServiceRSResponse>builder()
                .message("successful creation")
                .data(serviceRSService.saveService(request))
                .build();
    }

    // Xóa service
    @DeleteMapping("/delete_service/{idService}")
    public ApiRespone<String> deleteService(@PathVariable String idService)
    {
        serviceRSService.deleteService(idService);
        return ApiRespone.<String>builder()
                .message("successful deletion")
                .build();
    }

    // Sửa service
    @PutMapping("/update_service/{idService}")
    public ApiRespone<ServiceRSResponse> updateService(@RequestBody ServiceRSUpdateRequest request,
                                                       @PathVariable String idService)
    {
        return ApiRespone.<ServiceRSResponse>builder()
                .message("successful updating")
                .data(serviceRSService.updateService(request, idService))
                .build();
    }
}
