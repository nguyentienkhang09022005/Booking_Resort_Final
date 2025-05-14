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
    private String id_payment;

    @ManyToOne
    @JoinColumn(name = "id_br", nullable = false)
    private Booking_room id_br;

    private BigDecimal money;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime create_date;

    private String status;
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = "Chờ Thanh Toán";
        }
    }
    private String payment_method;
}
