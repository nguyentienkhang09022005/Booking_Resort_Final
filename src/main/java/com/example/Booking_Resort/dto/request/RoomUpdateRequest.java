package com.example.Booking_Resort.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomUpdateRequest
{
    String name_room;
    String id_type;
    BigDecimal price;
    String status;
    String describe_room;
    MultipartFile image;
}
