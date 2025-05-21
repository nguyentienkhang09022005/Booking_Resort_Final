package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "service")
public class ServiceRS
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_sv;

    @ManyToOne
    @JoinColumn(name = "id_rs", nullable = false)
    private Resort idRs;

    private String name_sv;
    private BigDecimal price;
    private String describe_service;
}
