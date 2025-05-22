package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment")
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idPayment;

    @ManyToOne
    @JoinColumn(name = "id_br", nullable = false)
    private Booking_room idBr;

    private BigDecimal money;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime create_date;

    private String payment_method;
}
