package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.EvaluateCreationRequest;
import com.example.Booking_Resort.dto.request.EvaluateUpdateRequest;
import com.example.Booking_Resort.dto.response.EvaluateRespone;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.mapper.EvaluateMapper;
import com.example.Booking_Resort.models.Evaluate;
import com.example.Booking_Resort.models.Resort;
import com.example.Booking_Resort.models.User;
import com.example.Booking_Resort.repository.EvaluateRepository;
import com.example.Booking_Resort.repository.ResortRepository;
import com.example.Booking_Resort.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvaluateService
{
    UserRepository userRepository;
    ResortRepository resortRepository;
    EvaluateRepository evaluateRepository;
    EvaluateMapper evaluateMapper;

    // Hàm lấy danh sách đánh giá
    public List<Evaluate> getAllEvaluate()
    {
        return evaluateRepository.findAll();
    }

    // Hàm thêm đánh giá vào csdl
    public EvaluateRespone saveEvaluate(EvaluateCreationRequest request)
    {
        User user =  userRepository.findById(request.getId_user()).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );

        Resort resort =  resortRepository.findById(request.getId_rs()).orElseThrow(
                () -> new ApiException(ErrorCode.RESORT_NOT_FOUND)
        );

        // Kiểm tra nếu đã đánh giá rồi thì không cho đánh giá nữa
        boolean isEvaluated = evaluateRepository.existsByIdUser_IdUserAndIdRs_IdRs(
                request.getId_user(), request.getId_rs());

        if (isEvaluated) {
            throw new ApiException(ErrorCode.EVALUATE_ALREADY_EXISTS);
        }

        Evaluate evaluate = evaluateMapper.toEvaluate(request);
        evaluate.setIdUser(user);
        evaluate.setIdRs(resort);
        Evaluate saveEvaluate = evaluateRepository.save(evaluate);

        var evaluateResponse = evaluateMapper.toEvaluateRespone(saveEvaluate);
        evaluateResponse.setIdEvaluate(saveEvaluate.getId_evaluate());
        evaluateResponse.setStar_rating(saveEvaluate.getStar_rating());
        return evaluateResponse;
    }

    // Hàm thay đổi đánh giá
    public EvaluateRespone changeEvaluate(EvaluateUpdateRequest request, String idEvaluate)
    {
        Evaluate evaluate = evaluateRepository.findById(idEvaluate).orElseThrow(
                () -> new ApiException(ErrorCode.EVALUATE_NOT_FOUND)
        );
        evaluateMapper.updateEvaluate(evaluate, request);
        var evaluateResponse = evaluateMapper.toEvaluateRespone(evaluateRepository.save(evaluate));
        evaluateResponse.setIdEvaluate(idEvaluate);
        evaluateResponse.setStar_rating(request.getStar_rating());
        return evaluateResponse;
    }

    // Hàm xóa đánh giá
    public void deleteEvaluate(String idEvaluate)
    {
        Evaluate evaluate = evaluateRepository.findById(idEvaluate).orElseThrow(
                () -> new ApiException(ErrorCode.EVALUATE_NOT_FOUND)
        );
        evaluateRepository.delete(evaluate);
    }

}
