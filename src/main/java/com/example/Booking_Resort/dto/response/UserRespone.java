package com.example.Booking_Resort.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRespone
{
    String idUser;
    String nameuser;
    String sex;
    String phone;
    String email;
    String identificationCard;
    LocalDate dob;
    String passport;
    String account;
    Set<RoleResponse> role_user;
    String avatar;
}
