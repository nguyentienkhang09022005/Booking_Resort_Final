package com.example.Booking_Resort.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Roles
{
    @Id
    @Column(name = "name_role")
    String name;

    String description;

    @ManyToMany
    Set<Permissions> permissions;
}
