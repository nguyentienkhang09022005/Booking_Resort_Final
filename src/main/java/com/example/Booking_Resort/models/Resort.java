package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "resort")
public class Resort
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_rs")
    private String idRs;

    @ManyToOne
    @JoinColumn(name = "id_owner", nullable = false)
    private User idOwner;
    private String name_rs;
    private String location_rs;
    private String describe_rs;

    @OneToMany(mappedBy = "idRs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}
