package com.example.Booking_Resort.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode
{
    // Lỗi người dùng
    USER_NOT_FOUND("USER_001", "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXIST("USER_002", "User not exist", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTS("USER_003", "User already exists", HttpStatus.BAD_REQUEST),
    FAILED_SEARCH_USER("USER_004", "Failed to search user", HttpStatus.INTERNAL_SERVER_ERROR),
    PASSWORDS_DO_NOT_MATCH("USER_005", "Password and confirm password do not match", HttpStatus.BAD_REQUEST),

    // Lỗi không xác nhận người dùng
    UNAUTHENTICATED("UNAUTHENTICATED", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UNAUTHORIZED", "You do not have permission", HttpStatus.FORBIDDEN),

    // Lỗi Resort
    RESORT_NOT_FOUND("RESORT_001", "Resort not found", HttpStatus.NOT_FOUND),

    // Lỗi Room
    ROOM_NOT_FOUND("ROOM_001", "Room not found", HttpStatus.NOT_FOUND),

    // Lỗi TypeRoom
    TYPE_NOT_FOUND("TYPE_001", "Type not found", HttpStatus.NOT_FOUND),

    // Lỗi Service
    SERVICE_NOT_FOUND("SERVICE_002", "Service not found", HttpStatus.NOT_FOUND),

    // Lỗi Evaluate
    EVALUATE_NOT_FOUND("EVALUATE_001", "Evaluate not found", HttpStatus.NOT_FOUND),
    EVALUATE_ALREADY_EXISTS("EVALUATE_002", "Evaluate already exists", HttpStatus.CONFLICT),

    // Lỗi Booking_room
    BOOKING_ROOM_NOT_FOUND("BOOKING_ROOM_001", "Booking room not found", HttpStatus.NOT_FOUND),
    BOOKING_CONFIRMED_CANNOT_DELETE_PAYMENT("BOOKING_ROOM_002", "Booking room cant not delete", HttpStatus.CONFLICT),

    // Lỗi Payment
    PAYMENT_NOT_FOUND("PAYMENT_001", "Payment not found", HttpStatus.NOT_FOUND),

    // Lỗi Expense
    EXPENSE_NOT_FOUND("EXPENSE_001", "Expense not found", HttpStatus.NOT_FOUND),

    // Lỗi Monthly_Report
    MONTHLYREPORT_NOT_FOUND("MONTHLYREPORT_001", "Monthly report not found", HttpStatus.NOT_FOUND),

    // Lỗi Role
    ROLES_NOT_FOUND("ROLES_001", "Roles not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTS("ROLE_002", "Role already exists", HttpStatus.BAD_REQUEST),

    // Lỗi Permission
    PERMISSIONS_NOT_FOUND("PERMISSIONS_001", "Permissions not found", HttpStatus.NOT_FOUND),
    PERMISSIONS_EXISTS("PERMISSIONS_002", "Permissions already exists", HttpStatus.BAD_REQUEST),

    // Lỗi báo cáo
    REPORT_NOT_FOUND("REPORT_001", "Report not found", HttpStatus.NOT_FOUND),

    // Lỗi chi tiết báo cáo
    DETAIL_REPORT_NOT_FOUND("DETAIL_REPORT_001", "Detail report not found", HttpStatus.NOT_FOUND),

    // Lỗi opt
    OTP_NOT_EXIST_FOR_EMAIL("OTP_001", "OTP not exist for email", HttpStatus.INTERNAL_SERVER_ERROR),

    // Lỗi lưu ảnh
    UPLOAD_FAILED("UPLOAD_FAILED", "Upload failed", HttpStatus.INTERNAL_SERVER_ERROR),

    // Lỗi khác
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus)
    {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
