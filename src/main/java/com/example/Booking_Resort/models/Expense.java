package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "expense")
public class Expense
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_expense;

    @ManyToOne
    @JoinColumn(name = "id_rs", nullable = false)
    private Resort idResort;
    private String category;
    private BigDecimal amount;

    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime create_date;
}
