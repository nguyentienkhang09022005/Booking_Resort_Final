package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp")
public class Otp
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_otp;

    private int otp;

    @Column(name = "expiration_time", updatable = false)// Không cho sửa dữ liệu khi đã lưu
    private LocalDateTime expiration_time;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "id_user")
    private User user;
}
