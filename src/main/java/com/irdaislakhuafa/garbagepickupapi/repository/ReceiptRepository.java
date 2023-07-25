package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, String> {
}