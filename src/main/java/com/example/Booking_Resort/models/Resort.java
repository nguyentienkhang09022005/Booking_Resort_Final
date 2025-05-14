package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "resort")
public class Resort
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_rs;

    @ManyToOne
    @JoinColumn(name = "id_owner", nullable = false)
    private User idOwner;
    private String name_rs;
    private String location_rs;
    private String describe_rs;
}
