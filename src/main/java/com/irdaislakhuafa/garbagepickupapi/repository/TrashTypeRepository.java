package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.TrashType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashTypeRepository extends JpaRepository<TrashType, String> {
}