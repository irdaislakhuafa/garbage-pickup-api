package com.irdaislakhuafa.garbagepickupapi.models.entities;

import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "pickup")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Pickup extends BaseEntity {
    @Column(nullable = false)
    private String resi;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User courier;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PickupStatus status;

    @Column
    private int weight; // in KG

    @Column(length = 5000, nullable = false)
    private String place;

    @JoinColumn(nullable = false)
    @ManyToOne
    private TrashType trashType;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn
    private UserVoucher userVoucher;

    @Column(nullable = false)
    private int price = 0;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;
}