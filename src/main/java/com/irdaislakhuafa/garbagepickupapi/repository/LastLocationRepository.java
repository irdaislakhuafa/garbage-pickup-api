package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.LastLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LastLocationRepository extends JpaRepository<LastLocation, String> {
}