package com.example.Booking_Resort.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest
{
    String nameuser;
    String sex;
    String phone;
    String email;
    String indentification_card;
    LocalDate dob;
    String passport;
    String account;
    String passworduser;
    MultipartFile avatar;
}
