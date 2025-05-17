package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "evaluate")
public class Evaluate
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_evaluate;

    @ManyToOne
    @JoinColumn(name = "id_rs", nullable = false)
    private Resort id_rs;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User id_user;

    private String user_comment;
    private Double start_rating;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private LocalDate create_date;
}
