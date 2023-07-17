package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;

public interface PickupRepository extends JpaRepository<Pickup, String> {
    HashSet<Pickup> findALlByUserId(String userId);
}