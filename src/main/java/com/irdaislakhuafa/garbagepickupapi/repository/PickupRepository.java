package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.HashSet;

public interface PickupRepository extends JpaRepository<Pickup, String> {
    HashSet<Pickup> findAllByUserIdAndCreatedAtBetween(String userId, LocalDateTime start, LocalDateTime end);

    HashSet<Pickup> findAllByUserIdAndCreatedAtBetweenAndStatus(String userId, LocalDateTime start, LocalDateTime end, PickupStatus status);
}