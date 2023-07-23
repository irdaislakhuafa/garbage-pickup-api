package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.PickupActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupActivityRepository extends JpaRepository<PickupActivity, PickupActivityRepository> {
}