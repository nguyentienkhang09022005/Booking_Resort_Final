package com.example.Booking_Resort.dto.request;

import com.example.Booking_Resort.models.Permissions;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest
{
    String name;
    String description;
    Set<String> permissions;
}
