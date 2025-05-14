package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "type_room")
public class Type_Room
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_type;

    @Column(name = "name_type")
    private String nameType;
}