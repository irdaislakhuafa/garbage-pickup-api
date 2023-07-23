package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.PickupActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupActivityRepository extends JpaRepository<PickupActivity, PickupActivityRepository> {
    List<PickupActivity> findAllByPickupId(String pickupId);

    List<PickupActivity> findAllByPickupResi(String pickupResi);
}