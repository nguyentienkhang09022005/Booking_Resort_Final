package com.example.Booking_Resort.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfResortResponse {
    String idRs;
    String name_rs;
    String location_rs;
    String describe_rs;
    String image;
    double star;
    boolean favorite;
    List<RoomRespone> rooms;
    List<EvaluateInfResortResponse> evaluates;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EvaluateInfResortResponse
    {
        String idEvaluate;
        String idUser;
        String nameuser;
        String avatar;
        String user_comment;
        Double star_rating;
        LocalDate create_date;

    }
}


