package com.example.Booking_Resort.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

import lombok.Data;

@Entity
@Data
@Table(name = "room")
public class Room
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_room;

    @ManyToOne
    @JoinColumn(name = "id_rs", nullable = false)
    private Resort id_rs;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private Type_Room id_type;

    private String name_room;
    private BigDecimal price;
    private int repair;

    private String status;
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = "Trống";
        }
    }

    private String describe_room;
}