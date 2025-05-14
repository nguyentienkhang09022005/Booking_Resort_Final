package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "monthly_report")
public class Monthly_Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idReport;

    @ManyToOne
    @JoinColumn(name = "id_rs", nullable = false)
    private Resort idResort;

    private Integer reportMonth;
    private Integer reportYear;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal netProfit;

    @CreationTimestamp
    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @OneToMany(mappedBy = "idReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detail_Report> details;
}
