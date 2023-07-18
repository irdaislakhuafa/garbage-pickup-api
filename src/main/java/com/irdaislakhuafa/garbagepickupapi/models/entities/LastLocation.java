package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "last_location")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LastLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private double lat;
    @Column(nullable = false)
    private double lng;
}