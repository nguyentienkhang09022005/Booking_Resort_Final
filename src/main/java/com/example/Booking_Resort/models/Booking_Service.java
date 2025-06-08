package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "booking_service")
public class Booking_Service
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_bk")
    private String idBk;

    @ManyToOne
    @JoinColumn(name = "id_sv", nullable = false)
    private ServiceRS idSV;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;

    @ManyToOne
    @JoinColumn(name = "id_br", nullable = false)
    private Booking_room idBr;

    @CreationTimestamp
    @Column(name = "booking_date", updatable = false)
    private LocalDateTime booking_date;

    private Integer quantity;
    private BigDecimal total_amount;
}
