package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.ServiceRSCreationRequest;
import com.example.Booking_Resort.dto.request.ServiceRSUpdateRequest;
import com.example.Booking_Resort.dto.response.ResortResponse;
import com.example.Booking_Resort.dto.response.ServiceRSResponse;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.ServiceRSMapper;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.ServiceRS;
import com.example.Booking_Resort.repository.ResortRepository;
import com.example.Booking_Resort.repository.ServiceRSRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceRSService
{
    ServiceRSRepository serviceRSRepository;
    ResortRepository resortRepository;
    ServiceRSMapper serviceRSMapper;

    // Hàm lấy danh sách dịch vụ
    public List<ServiceRS> getAllService()
    {
        return this.serviceRSRepository.findAll();
    }

    // Hàm thêm dịch vụ
    public ServiceRSResponse saveService(ServiceRSCreationRequest request)
    {
        Resort resort = resortRepository.findById(request.getId_rs()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        ServiceRS service = serviceRSMapper.toServiceRS(request);
        service.setId_rs(resort);
        return serviceRSMapper.toServiceRSResponse(serviceRSRepository.save(service));
    }

    // Hàm sửa dịch vụ
    public ServiceRSResponse updateService(ServiceRSUpdateRequest request, String idService)
    {
        ServiceRS service = serviceRSRepository.findById(idService).orElseThrow(
                () -> new ApiException(ErrorCode.SERVICE_NOT_FOUND)
        );

        serviceRSMapper.updateService(service, request);
        return serviceRSMapper.toServiceRSResponse(serviceRSRepository.save(service));
    }

    // Hàm xóa dịch vụ
    public void deleteService(String idService)
    {
        ServiceRS service = serviceRSRepository.findById(idService).orElseThrow(
                () -> new ApiException(ErrorCode.SERVICE_NOT_FOUND)
        );
        serviceRSRepository.delete(service);
    }
}
