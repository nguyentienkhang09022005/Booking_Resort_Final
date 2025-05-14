package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<Permissions, String> { }
