package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, String> {
    Optional<Receipt> findByPickupId(String pickupId);
}