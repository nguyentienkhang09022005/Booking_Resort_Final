package com.example.Booking_Resort.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResortCreationRequest {
    String idOwner;
    String name_rs;
    String location_rs;
    String describe_rs;
    MultipartFile image;
}
