package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "detail_report")
public class Detail_Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idDetail;

    @ManyToOne
    @JoinColumn(name = "id_report", nullable = false)
    private Monthly_Report idReport;

    private String type;
    private BigDecimal amount;

    @Column(name = "create_date")
    private LocalDateTime createDate;
}
