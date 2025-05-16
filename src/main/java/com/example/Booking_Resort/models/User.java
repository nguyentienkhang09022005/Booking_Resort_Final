package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user")
    private String idUser;

    @Column(name = "name_user")
    private String nameuser;

    private String sex;
    private String phone;
    private String email;

    @Column(name = "indentification_card")
    private String identificationCard;
    private LocalDate dob;
    private String passport;

    private String account;
    @Column(name = "password_user")
    private String passworduser;

    @ManyToMany
    Set<Roles> role_user;

    @OneToMany(mappedBy = "idUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}
