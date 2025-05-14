package com.example.Booking_Resort.controller;

import com.example.Booking_Resort.dto.request.EvaluateCreationRequest;
import com.example.Booking_Resort.dto.request.EvaluateUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.EvaluateRespone;
import com.example.Booking_Resort.models.Evaluate;
import com.example.Booking_Resort.service.EvaluateService;
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
@RequestMapping("/api/evaluate")
public class EvaluateController
{
    EvaluateService evaluateService;

    // Endpoint lấy danh sách đánh giá
    @GetMapping("/list_evaluate")
    public ApiRespone<List<Evaluate>> getAllEvaluate()
    {
        return ApiRespone.<List<Evaluate>>builder()
                .data(evaluateService.getAllEvaluate())
                .build();
    }

    // Endpoint tạo đánh giá
    @PostMapping("/create_evaluate")
    public ApiRespone<EvaluateRespone> createEvaluate(@RequestBody EvaluateCreationRequest request)
    {
        return ApiRespone.<EvaluateRespone>builder()
                .message("Create Evaluate Successful")
                .data(evaluateService.saveEvaluate(request))
                .build();
    }

    // Endpoint thay đổi đánh giá
    @PutMapping("/change_evaluate/{idEvaluate}")
    public ApiRespone<EvaluateRespone> changeEvaluate(@RequestBody EvaluateUpdateRequest request, @PathVariable ("idEvaluate") String idEvaluate)
    {
        return ApiRespone.<EvaluateRespone>builder()
                .message("Change Evaluate Successful")
                .data(evaluateService.changeEvaluate(request, idEvaluate))
                .build();
    }

    // Endpoint xóa đánh giá
    @DeleteMapping("/delete_evaluate/{idEvaluate}")
    public ApiRespone<String> deleteEvaluate(@PathVariable ("idEvaluate") String idEvaluate)
    {
        evaluateService.deleteEvaluate(idEvaluate);
        return ApiRespone.<String>builder()
                .message("Delete Evaluate Successful")
                .build();
    }
}
