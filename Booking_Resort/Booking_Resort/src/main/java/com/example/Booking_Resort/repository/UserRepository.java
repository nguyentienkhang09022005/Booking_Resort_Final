package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    Optional<User> findByAccount(String account);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);

    Boolean existsByAccount(String account);
    Boolean existsByEmail(String email);
}
