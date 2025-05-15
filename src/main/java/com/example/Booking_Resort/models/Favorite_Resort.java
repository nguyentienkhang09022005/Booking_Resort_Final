package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "favorite_resort")
public class Favorite_Resort
{
    @EmbeddedId
    private FavoriteResortKey id;

    @ManyToOne
    @MapsId("id_user")
    @JoinColumn(name = "id_user", nullable = false)
    private User id_user;

    @ManyToOne
    @MapsId("id_rs")
    @JoinColumn(name = "id_rs", nullable = false)
    private Resort id_rs;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;
}
