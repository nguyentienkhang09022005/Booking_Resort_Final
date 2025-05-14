package com.example.Booking_Resort.exception;

import com.example.Booking_Resort.dto.response.ApiRespone;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiRespone> handlingAccessDeniedException(AccessDeniedException exception)
    {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(
                ApiRespone.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<ApiRespone> handlingAppException(ConstraintViolationException exception) {
        ApiRespone apiRespone = new ApiRespone();

        apiRespone.setMessage(exception.getCause().getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiRespone);
    }

    @ExceptionHandler(value = ApiException.class)
    ResponseEntity<ApiRespone> handlingAppException(ApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiRespone apiRespone = new ApiRespone();

        apiRespone.setCode(errorCode.getCode());
        apiRespone.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiRespone);
    }

}
