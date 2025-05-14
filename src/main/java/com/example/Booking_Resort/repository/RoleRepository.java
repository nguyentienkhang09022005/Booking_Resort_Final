package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String>
{
}
