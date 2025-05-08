package com.example.Booking_Resort.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRespone
{
    String nameuser;
    String sex;
    String phone;
    String email;
    String indentification_card;
    LocalDate dob;
    String passport;
    String account;
    Set<RoleResponse> role_user;
    String avatar;
}
