package com.example.Booking_Resort.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest
{
    String nameuser;

    String sex;
    String phone;
    String email;
    String indentification_card;
    LocalDate dob;
    String passport;

    String passworduser;
    List<String> role_user;
    MultipartFile avatar;
}
