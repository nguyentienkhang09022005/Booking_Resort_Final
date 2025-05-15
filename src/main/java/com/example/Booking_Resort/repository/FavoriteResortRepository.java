package com.example.Booking_Resort.repository;

import com.example.Booking_Resort.models.FavoriteResortKey;
import com.example.Booking_Resort.models.Favorite_Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteResortRepository extends JpaRepository<Favorite_Resort, FavoriteResortKey> { }
