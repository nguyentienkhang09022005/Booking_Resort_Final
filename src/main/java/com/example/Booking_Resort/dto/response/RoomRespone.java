package com.example.Booking_Resort.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomRespone
{
    String idRoom;
    String name_room;
    String type_room;
    BigDecimal price;
    String status;
    String describe_room;
    String image;
}
