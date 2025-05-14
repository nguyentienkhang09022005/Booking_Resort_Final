package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "image")
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_img;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_rs")
    private Resort idRs;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_room")
    private Room idRoom;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_user")
    private User idUser;

    private String url;
}
