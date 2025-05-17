package com.example.Booking_Resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResortResponse {
    String idRs;
    String name_rs;
    String location_rs;
    String describe_rs;
    String image;
    double star;
    boolean favorite;
    List<RoomRespone> rooms;
    List<EvaluateRespone> evaluates;
}

