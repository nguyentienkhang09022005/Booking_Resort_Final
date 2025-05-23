package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "booking_room")
public class Booking_room
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idBr;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;

    @ManyToOne
    @JoinColumn(name = "id_room", nullable = false)
    private Room idRoom;

    private LocalDateTime checkinday;
    private LocalDateTime checkoutday;
    private BigDecimal total_amount;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime create_date;

    private String status;
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = "Chờ Xác Nhận";
        }
    }
}
