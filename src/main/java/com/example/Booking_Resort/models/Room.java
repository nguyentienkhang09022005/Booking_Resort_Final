package com.example.Booking_Resort.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Entity
@Data
@Table(name = "room")
public class Room
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idRoom;

    @ManyToOne
    @JoinColumn(name = "id_rs", nullable = false)
    private Resort idRs;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private Type_Room id_type;

    private String name_room;
    private BigDecimal price;

    private String status;
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = "Trống";
        }
    }

    private String describe_room;

    @OneToMany(mappedBy = "idRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}